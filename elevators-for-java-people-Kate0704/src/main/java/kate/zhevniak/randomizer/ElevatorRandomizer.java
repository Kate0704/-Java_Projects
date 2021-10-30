package kate.zhevniak.randomizer;

import kate.zhevniak.domain.Elevator;

import java.util.List;

public class ElevatorRandomizer extends Randomizer<Elevator> {
    public static final List<Integer> AVAILABLE_CAPACITY_LIST =
            List.of(225, 300, 320, 400, 500, 630, 1000, 1275);

    private int ID = 1;

    public Elevator next() {
        return new Elevator(ID++, randomizeElevatorCapacity());
    }

    private int randomizeElevatorCapacity() {
        int index = randomizeFromTo(AVAILABLE_CAPACITY_LIST.size());
        return AVAILABLE_CAPACITY_LIST.get(index);
    }
}
