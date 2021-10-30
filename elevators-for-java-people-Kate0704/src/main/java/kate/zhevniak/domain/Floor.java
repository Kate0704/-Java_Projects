package kate.zhevniak.domain;

import kate.zhevniak.util.Call;
import kate.zhevniak.util.Direction;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import static kate.zhevniak.randomizer.PassengerRandomizer.MIN_WEIGHT;
import static kate.zhevniak.util.Direction.UP;

@Slf4j
@ToString
public class Floor {
    private final int number;
    private final BlockingQueue<Passenger> queueUp;
    private final BlockingQueue<Passenger> queueDown;

    public Floor(int number) {
        this.number = number;
        queueUp = new LinkedBlockingQueue<>();
        queueDown = new LinkedBlockingQueue<>();
    }

    public Optional<List<Passenger>> providePassengersToPickUp(Direction direction, int availableWeight) {
        Queue<Passenger> queue = getQueueByDirection(direction);
        if (queue.isEmpty()) {
            log.debug("#{} no passengers to pick", number);
            return Optional.empty();
        }
        List<Passenger> list = new ArrayList<>();
        for (Passenger passenger : queue) {
            if (passenger.getWeight() < availableWeight) {
                queue.poll();
                list.add(passenger);
            }
            availableWeight -= passenger.getWeight();
            if (availableWeight < MIN_WEIGHT) break;
        }
        return Optional.of(list);
    }

    public Optional<Call> getRepeatedCall(Direction direction) {
        Queue<Passenger> passengers = getQueueByDirection(direction);
        return passengers.isEmpty() ? Optional.empty() : Optional.ofNullable(passengers.peek().callFromFloor());
    }

    public Optional<Call> addToWaitingQueue(@NotNull Passenger passenger) {
        Queue<Passenger> queue = getQueueByDirection(passenger.getDirection());
        queue.add(passenger);
//        log.debug("#{} add passenger={}, upQueueSize={}", number, passenger, queue.size());
        Call call = passenger.callFromFloor();
        return queue.size() > 1 ? Optional.empty() : Optional.ofNullable(call);
    }

    private Queue<Passenger> getQueueByDirection(@NotNull Direction direction) {
        return direction == UP ? queueUp : queueDown;
    }

}
