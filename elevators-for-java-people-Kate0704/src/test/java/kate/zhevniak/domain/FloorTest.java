package kate.zhevniak.domain;

import kate.zhevniak.util.Call;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static kate.zhevniak.util.Direction.DOWN;
import static kate.zhevniak.util.Direction.UP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
class FloorTest {
    Floor floor;
    Passenger passenger;

    @BeforeEach
    void setUp() {
        floor = new Floor(1);
        passenger = new Passenger(50, 2, 3);
        floor.addToWaitingQueue(passenger);
    }

    @Test
    void providePassengersToPickUp() {
        Optional<List<Passenger>> passengers = floor.providePassengersToPickUp(UP, 100);
        assertThat(passengers.get().get(0), equalTo(passenger));
    }

    @Test
    void providePassengersToPickUp_EmptyQueue() {
        Optional<List<Passenger>> passengers = floor.providePassengersToPickUp(DOWN, 100);
        assertThat(passengers.isPresent(), is(false));
    }

    @Test
    void getRepeatedCall() {
        Optional<Call> repeatedCall = floor.getRepeatedCall(UP);
        assertThat(repeatedCall.get().floor(), equalTo(2));
    }

    @Test
    void testToString() {
        assertThat(floor.toString(),
                equalTo("Floor(number=1, " +
                        "queueUp=[Passenger(weight=50, currentFloor=2, requiredFloor=3, direction=UP)], " +
                        "queueDown=[])"));
    }
}