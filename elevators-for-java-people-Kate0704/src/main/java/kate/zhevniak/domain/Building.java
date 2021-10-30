package kate.zhevniak.domain;

import kate.zhevniak.algorithm.ElevatorPicker;
import kate.zhevniak.spawner.ElevatorSpawn;
import kate.zhevniak.spawner.PeopleSpawn;
import kate.zhevniak.statistics.StatisticsWriter;
import kate.zhevniak.util.Call;
import kate.zhevniak.util.Direction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static java.util.Collections.min;
import static kate.zhevniak.domain.Elevator.TIME_TO_OPEN_CLOSE_DOORS;
import static kate.zhevniak.util.Direction.*;

@Getter
@Slf4j
public class Building implements Elevator.PassengerProvider {
    private final int numberOfFloors;
    private final int numberOfElevators;
    private final int peopleSpawnFrequency;
    private final Floor[] floors;
    private final Elevator[] elevators;
    private final BlockingQueue<Call> waitingCalls;
    private final StatisticsWriter statisticsWriter;

    @Getter(value = AccessLevel.NONE)
    private final PeopleSpawn peopleSpawn;
    @Getter(value = AccessLevel.NONE)
    private final ReentrantLock lock;
    @Getter(value = AccessLevel.NONE)
    private final Condition hasWaitingCalls;

    @Setter
    private volatile boolean isRunning;

    public Building(int numberOfFloors, int numberOfElevators, int peopleSpawnFrequency) {
        log.debug("create building");
        this.numberOfFloors = numberOfFloors;
        this.numberOfElevators = numberOfElevators;
        this.peopleSpawnFrequency = peopleSpawnFrequency;
        peopleSpawn = new PeopleSpawn(this::acceptPassenger, numberOfFloors, peopleSpawnFrequency);
        floors = IntStream.range(0, numberOfFloors)
                .mapToObj(Floor::new)
                .toArray(Floor[]::new);
        elevators = IntStream.range(0, numberOfElevators)
                .mapToObj(ElevatorSpawn::spawn)
                .toArray(Elevator[]::new);

        Arrays.stream(elevators).forEach(this::providePassengersSupplier);
        statisticsWriter = new StatisticsWriter(numberOfFloors, numberOfElevators);
        IntStream.range(0, numberOfElevators)
                .forEach(i -> elevators[i]
                        .setStatistics(statisticsWriter.getStatisticsOfElevator(i)));
        waitingCalls = new LinkedBlockingQueue<>();
        lock = new ReentrantLock();
        hasWaitingCalls = lock.newCondition();
        isRunning = true;
    }

    public Elevator[] getElevators() {
        return Arrays.copyOf(elevators, elevators.length);
    }

    public Floor[] getFloors() {
        return Arrays.copyOf(floors, floors.length);
    }

    public @NotNull @UnmodifiableView List<Call> getWaitingCalls() {
        return Collections.unmodifiableList(waitingCalls.stream().toList());
    }

    private void acceptPassenger(@NotNull Passenger passenger) {
        Optional<Call> call = floors[passenger.getCurrentFloor()].addToWaitingQueue(passenger);
        call.ifPresent(this::addToWaitingCalls);
    }

    private void addToWaitingCalls(Call call) {
        waitingCalls.add(call);
        signalHasWaitingCalls();
    }

    private void signalHasWaitingCalls() {
        lock.lock();
        hasWaitingCalls.signal();
        lock.unlock();
    }

    public void run() {
        new Thread(peopleSpawn).start();
        Arrays.stream(elevators).map(Thread::new).forEach(Thread::start);
        new Thread(statisticsWriter).start();
        processWaitingQueue();
    }

    private void providePassengersSupplier(@NotNull Elevator elevator) {
        elevator.setPassengerProvider(this);
    }

    @SneakyThrows
    public Optional<List<Passenger>> providePassengers(int elevatorId, int floorNumber,
                                                       Direction direction, int availableWeight) {
        Floor floor = floors[floorNumber];
        Optional<List<Passenger>> passengers;
        Optional<Call> repeatedCall;

        if (floorNumber == 0) {
            passengers = floor.providePassengersToPickUp(UP, availableWeight);
            repeatedCall = floor.getRepeatedCall(UP);
        } else if (floorNumber == numberOfFloors - 1) {
            passengers = floor.providePassengersToPickUp(DOWN, availableWeight);
            repeatedCall = floor.getRepeatedCall(DOWN);
        } else {
            passengers = floor.providePassengersToPickUp(direction, availableWeight);
            repeatedCall = direction == UP ? floor.getRepeatedCall(UP) : floor.getRepeatedCall(DOWN);
        }
        if (repeatedCall.isPresent()) {
            TimeUnit.MILLISECONDS.sleep(TIME_TO_OPEN_CLOSE_DOORS * 2);
            addToWaitingCalls(new Call(floorNumber, repeatedCall.get().direction()));
        }
        log.debug("elevator #{} on floor #{} pick up passengers={}", elevatorId, floorNumber, passengers);
        return passengers;
    }

    @SneakyThrows
    private void processWaitingQueue() {
        while (isRunning) {
            lock.lock();
            try {
                while (waitingCalls.isEmpty()) {
                    hasWaitingCalls.await();
                }
            } finally {
                lock.unlock();
            }
            log.warn("Queue start process");
            while (!waitingCalls.isEmpty()) {
                Call call = waitingCalls.peek();
                if (handleCall(call)) {
                    waitingCalls.poll();
                }
            }
        }
    }

    private boolean handleCall(Call call) {
        Optional<Elevator> elevator = ElevatorPicker.getMostSuitableElevator(call, elevators);
        elevator.ifPresent(e -> e.addCallToQueue(call));
        return elevator.isPresent();
    }

}
