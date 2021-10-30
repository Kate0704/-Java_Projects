package kate.zhevniak.spawner;

import kate.zhevniak.domain.Elevator;
import kate.zhevniak.randomizer.ElevatorRandomizer;

public class ElevatorSpawn {
    private static final ElevatorRandomizer elevatorRandomizer = new ElevatorRandomizer();

    public static Elevator spawn(int i) {
        return elevatorRandomizer.next();
    }
}
