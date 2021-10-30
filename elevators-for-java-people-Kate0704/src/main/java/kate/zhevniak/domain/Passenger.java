package kate.zhevniak.domain;

import com.google.common.base.Preconditions;
import kate.zhevniak.util.Call;
import kate.zhevniak.util.Direction;
import lombok.Getter;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkArgument;
import static kate.zhevniak.util.Direction.DOWN;
import static kate.zhevniak.util.Direction.UP;

@Getter
@ToString
public class Passenger {
    private final int weight;
    private final int currentFloor;
    private final int requiredFloor;
    private final Direction direction;

    public Passenger(int weight, int currentFloor, int requiredFloor) {
        checkArgument(currentFloor != requiredFloor,
                "Passenger's current and requested floors must be different.");
        this.weight = weight;
        this.currentFloor = currentFloor;
        this.requiredFloor = requiredFloor;
        direction = requiredFloor > currentFloor ? UP : DOWN;
    }

    public Call callFromFloor() {
        return new Call(currentFloor, direction);
    }

    public Call callFromElevator() {
        return new Call(requiredFloor, direction);
    }

}
