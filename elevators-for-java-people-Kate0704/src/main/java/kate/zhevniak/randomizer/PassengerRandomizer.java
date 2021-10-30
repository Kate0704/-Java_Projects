package kate.zhevniak.randomizer;

import kate.zhevniak.domain.Passenger;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class PassengerRandomizer extends Randomizer<Passenger> {
    public static final int MIN_WEIGHT = 20;
    public static final int MAX_WEIGHT = 200;
    private final int numberOfFloors;

    public @NotNull Passenger next() {
        int currentFloor = randomizeCurrentFloor();
        return new Passenger(randomizeWeight(),
                currentFloor,
                randomizeRequiredFloor(currentFloor));
    }

    private int randomizeWeight() {
        return randomizeFromTo(MIN_WEIGHT, MAX_WEIGHT, true);
    }

    private int randomizeCurrentFloor() {
        return randomizeFromTo(numberOfFloors);
    }

    private int randomizeRequiredFloor(int currentFloor) {
        int requiredFloor = randomizeFromTo(numberOfFloors);
        while (requiredFloor == currentFloor) {
            requiredFloor = randomizeFromTo(numberOfFloors);
        }
        return requiredFloor;
    }

}
