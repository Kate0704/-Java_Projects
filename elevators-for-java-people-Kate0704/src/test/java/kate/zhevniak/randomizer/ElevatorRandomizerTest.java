package kate.zhevniak.randomizer;

import kate.zhevniak.domain.Elevator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static kate.zhevniak.randomizer.ElevatorRandomizer.AVAILABLE_CAPACITY_LIST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ElevatorRandomizerTest {
    ElevatorRandomizer randomizer;

    @Test
    void next() {
        randomizer = new ElevatorRandomizer();
        Elevator elevator = randomizer.next();
        assertThat(elevator.getId(), is(1));
        assertThat(AVAILABLE_CAPACITY_LIST.contains(elevator.getCapacity()), is(true));

        Elevator elevator2 = randomizer.next();
        assertThat(elevator2.getId(), is(2));
        assertThat(AVAILABLE_CAPACITY_LIST.contains(elevator2.getCapacity()), is(true));
    }
}