package kate.zhevniak.domain;

import kate.zhevniak.statistics.Statistics;
import kate.zhevniak.util.Call;
import kate.zhevniak.util.Direction;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static kate.zhevniak.util.Direction.DOWN;
import static kate.zhevniak.util.Direction.NO;
import static kate.zhevniak.util.Direction.UP;

@Slf4j
@Getter
@ToString(of = "id")
public class Elevator implements Runnable {
    public static final int TIME_TO_OPEN_CLOSE_DOORS = 100;
    private static final int TIME_TO_PASS_ONE_FLOOR = 500;

    private final int id;
    private Direction elevatorDirection;
    private Direction callDirection;
    private int currentFloor;
    private int availableCapacity;
    private final int capacity;

    private final List<Passenger> passengers;
    private final SortedSet<Call> calls;

    @Getter(value = AccessLevel.NONE)
    private final Condition hasCalls;
    @Getter(value = AccessLevel.NONE)
    private final ReentrantLock lock;
    @Setter
    private Statistics statistics;
    @Setter
    private PassengerProvider passengerProvider;

    public Elevator(int id, int capacity) {
        elevatorDirection = NO;
        callDirection = NO;
        currentFloor = 0;
        availableCapacity = capacity;
        passengers = new ArrayList<>();
        calls = new TreeSet<>();
        lock = new ReentrantLock();
        hasCalls = lock.newCondition();
        this.id = id;
        this.capacity = capacity;
        log.debug("Create elevator #{} capacity={}", id, capacity);
    }

    public List<Passenger> getPassengers() {
        return Collections.unmodifiableList(passengers);
    }

    public List<Call> getCalls() {
        return Collections.unmodifiableList(calls.stream().toList());
    }

    synchronized public void addCallToQueue(Call call) {
        if (!calls.contains(call)) {
            log.debug("#{} has new call {}", id, call);
        }
        calls.add(call);
        updateDirections(call);
        signalThatHasCalls();
    }

    private void updateDirections(@NotNull Call call) {
        callDirection = call.direction();
        if (!passengers.isEmpty()) {
            elevatorDirection = callDirection;
        } else if (elevatorDirection == NO) {
            elevatorDirection = call.floor() > currentFloor ? UP : DOWN;
        }
    }

    private void signalThatHasCalls() {
        lock.lock();
        hasCalls.signalAll();
        lock.unlock();
    }

    public void run() {
        while (!Thread.interrupted()) {
            waitForCalls();
            while (!needsStop()) {
                move();
            }
            openDoors();
            dropOffPassengers();
            pickUpPassengers();
            closeDoors();
        }
    }

    @SneakyThrows
    private void waitForCalls() {
        logWaitForCalls();
        lock.lock();
        try {
            while (elevatorDirection == NO)
                hasCalls.await();
        } finally {
            lock.unlock();
        }
    }

    private void logWaitForCalls() {
        if (elevatorDirection == NO) {
            log.debug("#{} is waiting for calls", id);
        } else {
            log.debug("#{} currentCall={}", id, getCurrentCall());
        }
    }

    @SneakyThrows
    private void move() {
        log.debug("#{} is moving {}", id, elevatorDirection);

        TimeUnit.MILLISECONDS.sleep(TIME_TO_PASS_ONE_FLOOR);
        currentFloor += (elevatorDirection == UP) ? 1 : -1;

        statistics.incrementPassedFloors();

        log.debug("#{} is on the {} floor", id, currentFloor);
    }

    private boolean needsStop() {
        boolean needsStop = currentFloor == getCurrentCall().floor();
        log.debug("#{} needsStop={}", id, needsStop);
        return needsStop;
    }

    @SneakyThrows
    private void openDoors() {
        log.debug("#{} is opening doors", id);
        updateDirections(getCurrentCall());
        TimeUnit.MILLISECONDS.sleep(TIME_TO_OPEN_CLOSE_DOORS);
    }

    private void dropOffPassengers() {
        log.debug("#{} passengers={}", id, passengers);
        if (passengers.isEmpty()) {
            return;
        }

        log.debug("#{} drop off passengers", id);
        List<Passenger> dropOffList = passengers.stream()
                .filter(passenger -> passenger.getRequiredFloor() == currentFloor)
                .collect(Collectors.toList());
        passengers.removeAll(dropOffList);

        availableCapacity += dropOffList.stream().mapToInt(Passenger::getWeight).sum();

        statistics.addToFloor(currentFloor, dropOffList.size());
        statistics.addTransportedPassengersAmount(dropOffList.size());
        log.debug("#{} passengers={}", id, passengers);
    }

    private Call getCurrentCall() {
        return callDirection == UP ? calls.first() : calls.last();
    }

    private void pickUpPassengers() {
        log.debug("#{} pick up passengers", id);
        Optional<List<Passenger>> passengerList = passengerProvider.providePassengers(id,
                currentFloor,
                callDirection,
                availableCapacity);
        if (passengerList.isPresent()) {
            passengers.addAll(passengerList.get());
            passengerList.get().forEach(this::performCallsFromElevator);

            availableCapacity -= passengerList.get().stream().mapToInt(Passenger::getWeight).sum();
            statistics.addFromFloor(currentFloor, passengerList.get().size());
        }
        log.debug("#{} passengers={}", id, passengers);
        log.debug("#{} available capacity={}", id, availableCapacity);
    }

    private void performCallsFromElevator(@NotNull Passenger passenger) {
        Call call = passenger.callFromElevator();
        addCallToQueue(call);
    }

    @SneakyThrows
    private void closeDoors() {
        log.debug("#{} is closing doors", id);
        calls.remove(getCurrentCall());
        if (calls.isEmpty()) {
            log.debug("#{} IS IDLE", id);
            elevatorDirection = NO;
            callDirection = NO;
        }
        statistics.incrementCallsAmount();
        TimeUnit.MILLISECONDS.sleep(TIME_TO_OPEN_CLOSE_DOORS);
    }

    @FunctionalInterface
    public interface PassengerProvider {
        Optional<List<Passenger>> providePassengers(int elevatorId, int floor,
                                                    Direction callDirection, int availableWeight);
    }
}