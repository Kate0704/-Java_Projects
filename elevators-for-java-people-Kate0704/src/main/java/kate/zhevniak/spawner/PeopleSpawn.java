package kate.zhevniak.spawner;

import kate.zhevniak.domain.Passenger;
import kate.zhevniak.randomizer.PassengerRandomizer;
import kate.zhevniak.randomizer.Randomizer;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Getter
@ToString(of = {"numberOfFloors", "frequency"})
public class PeopleSpawn implements Runnable {
    private final Consumer<Passenger> passengerAcceptor;
    private final int numberOfFloors;
    private final int frequency;
    private final Randomizer<Passenger> randomizer;

    public PeopleSpawn(Consumer<Passenger> passengerAcceptor, int numberOfFloors, int frequency) {
        this.passengerAcceptor = passengerAcceptor;
        this.numberOfFloors = numberOfFloors;
        this.frequency = frequency;
        randomizer = new PassengerRandomizer(numberOfFloors);
    }

    @SneakyThrows
    public void run() {
        while (!Thread.interrupted()) {
            acceptPassenger();
            Thread.yield();
            TimeUnit.MILLISECONDS.sleep(frequency);
        }
    }

    private void acceptPassenger() {
        passengerAcceptor.accept(randomizer.next());
    }
}
