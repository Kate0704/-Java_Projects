package kate.zhevniak.domain;

import kate.zhevniak.util.Call;
import kate.zhevniak.util.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static kate.zhevniak.util.Direction.UP;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {
    Passenger passenger;
    Call callFromFloor;
    Call callFromElevator;

    @BeforeEach
    void setUp() {
        passenger = new Passenger(50, 1, 3);
        callFromFloor = passenger.callFromFloor();
        callFromElevator = passenger.callFromElevator();
    }

    @Test
    void testConstructor() {
        assertThat(passenger.getRequiredFloor(), equalTo(3));
        assertThrows(IllegalArgumentException.class, () -> new Passenger(50, 1, 1));
    }

    @Test
    void callFromFloor() {
        assertThat(callFromFloor.floor(), equalTo(1));
        assertThat(callFromFloor.direction(), equalTo(UP));
    }

    @Test
    void callFromElevator() {
        assertThat(callFromElevator.floor(), equalTo(3));
        assertThat(callFromElevator.direction(), equalTo(UP));
    }

    @Test
    void testToString() {
        System.out.println(passenger.toString());
    }
}