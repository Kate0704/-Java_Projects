package kate.zhevniak.randomizer;

import kate.zhevniak.domain.Passenger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static kate.zhevniak.randomizer.PassengerRandomizer.MAX_WEIGHT;
import static kate.zhevniak.randomizer.PassengerRandomizer.MIN_WEIGHT;
import static kate.zhevniak.util.Direction.DOWN;
import static kate.zhevniak.util.Direction.UP;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PassengerRandomizerTest {
    PassengerRandomizer randomizer;
    PassengerRandomizer randomizerMock;

    @BeforeEach
    void setUp() {
        randomizer = new PassengerRandomizer(5);
        randomizerMock = mock(PassengerRandomizer.class);
    }

    @Test
    void randomizeFromTo() {
        int randFromTo = randomizer.randomizeFromTo(50, 100, true);
        assertThat( randFromTo - 50 >= 0, is(true));
        assertThat( randFromTo - 100 < 0, is(true));
    }

    @Test
    void randomizeTo() {
        int randFromTo = randomizer.randomizeFromTo(5);
        assertThat( randFromTo >= 0, is(true));
        assertThat( randFromTo - 5 < 0, is(true));
    }

    @Test
    void next() {
        Passenger passenger = randomizer.next();
        assertThat( passenger.getWeight() - MIN_WEIGHT >= 0, is(true));
        assertThat( passenger.getWeight() - MAX_WEIGHT < 0, is(true));
        assertThat( passenger.getCurrentFloor() >= 0, is(true));
        assertThat( passenger.getCurrentFloor() - 5 < 0, is(true));
        assertThat( passenger.getRequiredFloor() >= 0, is(true));
        assertThat( passenger.getRequiredFloor() - 5 < 0, is(true));

        assertThat( passenger.getRequiredFloor() != passenger.getCurrentFloor(), is(true));
        assertThat( passenger.getDirection(),
                is(passenger.getRequiredFloor() > passenger.getCurrentFloor() ? UP : DOWN));
    }

    @Test
    void next_FloorsAreEqual() {
        Passenger passenger;
        for (int i = 0; i < 10000; i++) {
            passenger = randomizer.next();
            assertThat( passenger.getRequiredFloor() != passenger.getCurrentFloor(), is(true));
        }
    }
}