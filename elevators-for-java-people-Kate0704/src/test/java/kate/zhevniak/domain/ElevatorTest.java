package kate.zhevniak.domain;

import kate.zhevniak.statistics.Statistics;
import kate.zhevniak.util.Call;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static kate.zhevniak.util.Direction.UP;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ElevatorTest {
    Elevator elevator;
    Elevator elevatorMock;
    Building building;
    Passenger passenger;
    Call call;

    @BeforeEach
    void setUp() {
        elevator = new Elevator(1, 100);
        elevator.setStatistics(new Statistics(5));
        elevatorMock = mock(Elevator.class);
        building = mock(Building.class);
        passenger = new Passenger(50, 2, 3);
        call = passenger.callFromFloor();
    }

    @Test
    void addCallToQueue() {
        elevator.addCallToQueue(call);
        assertThat(elevator.getCalls().size(), equalTo(1));
        assertThat(elevator.getCalls().get(0), equalTo(call));
        assertThat(elevator.getElevatorDirection() == UP, is(true));
        assertThat(elevator.getCallDirection() == UP, is(true));
        assertThat(elevator.getPassengers().size(), equalTo(0));
        assertThat(elevator.getAvailableCapacity(), equalTo(100));
        assertThat(elevator.getCurrentFloor(), equalTo(0));
    }

    @SneakyThrows
    @Test
    void run() {
        elevator.setPassengerProvider(building);
        Floor floor = new Floor(2);
        floor.addToWaitingQueue(passenger);
        when(building.providePassengers(1, 2, UP, 100))
                .thenReturn(floor.providePassengersToPickUp(UP, 100));
        Thread thread = new Thread(elevator);
        thread.start();
        elevator.addCallToQueue(call);
        TimeUnit.MILLISECONDS.sleep(1000);
        assertThat(elevator.getStatistics().getTransportedPassengersAmount(), equalTo(1));
        assertThat(elevator.getStatistics().getFromFloorTop(), equalTo(List.of(2)));
        assertThat(elevator.getStatistics().getToFloorTop(), equalTo(List.of(3)));
        assertThat(Arrays.equals(elevator.getStatistics().getFromFloor(), new int[]{0, 0, 1, 0, 0}), is(true));
        assertThat(Arrays.equals(elevator.getStatistics().getToFloor(), new int[]{0, 0, 0, 1, 0}), is(true));
        assertThat(elevator.getStatistics().getNumberOfCalls(), equalTo(2));
        assertThat(elevator.getStatistics().getPassedFloorsAmount(), equalTo(3));
        assertThat(elevator.getPassengerProvider(), notNullValue());
        Thread.currentThread().interrupt();
    }

    @Test
    void testToString() {
        assertThat(elevator.toString(), equalTo("Elevator(id=1)"));
    }

}