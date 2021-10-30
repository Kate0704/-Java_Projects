package kate.zhevniak.algorithm;

import kate.zhevniak.domain.Elevator;
import kate.zhevniak.util.Call;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.IntStream;

import static kate.zhevniak.util.Direction.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("OptionalGetWithoutIsPresent")
class ElevatorPickerTest {
    Elevator[] elevators;
    Call newCall;
    Call currCall;
    List<Call> calls;

    @BeforeEach
    void setUp() {
        elevators = IntStream.range(0, 2)
                .mapToObj(i -> mock(Elevator.class))
                .toArray(Elevator[]::new);
        newCall = new Call(2, UP);
        currCall = new Call(3, UP);
        calls = new ArrayList<>();
        calls.add(currCall);
    }

    @Test
    void getMostSuitableElevator_AllIdle() {
        when(elevators[0].getElevatorDirection()).thenReturn(NO);
        when(elevators[1].getElevatorDirection()).thenReturn(NO);
        assertThat(ElevatorPicker.getMostSuitableElevator(newCall, elevators).get(), equalTo(elevators[0]));
    }

    @Test
    void getMostSuitableElevator_OneIdle() {
        when(elevators[0].getElevatorDirection()).thenReturn(UP);
        when(elevators[1].getElevatorDirection()).thenReturn(NO);
        assertThat(ElevatorPicker.getMostSuitableElevator(newCall, elevators).get(), equalTo(elevators[1]));
    }

    @Test
    void getMostSuitableElevator_BothUp_SecondHasMoreAvailableCapacity() {
        when(elevators[0].getElevatorDirection()).thenReturn(UP);
        when(elevators[1].getElevatorDirection()).thenReturn(UP);
        when(elevators[0].getCallDirection()).thenReturn(UP);
        when(elevators[1].getCallDirection()).thenReturn(UP);
        when(elevators[0].getCurrentFloor()).thenReturn(1);
        when(elevators[1].getCurrentFloor()).thenReturn(1);
        when(elevators[0].getAvailableCapacity()).thenReturn(100);
        when(elevators[1].getAvailableCapacity()).thenReturn(200);
        when(elevators[0].getCalls()).thenReturn(calls);
        when(elevators[1].getCalls()).thenReturn(calls);
        assertThat(ElevatorPicker.getMostSuitableElevator(newCall, elevators).get(), equalTo(elevators[1]));
    }

    @Test
    void getMostSuitableElevator_BothUp_OneOnTheWay() {
        when(elevators[0].getElevatorDirection()).thenReturn(UP);
        when(elevators[1].getElevatorDirection()).thenReturn(UP);
        when(elevators[0].getCallDirection()).thenReturn(UP);
        when(elevators[1].getCallDirection()).thenReturn(UP);
        when(elevators[0].getCurrentFloor()).thenReturn(1);
        when(elevators[1].getCurrentFloor()).thenReturn(3);
        when(elevators[0].getAvailableCapacity()).thenReturn(100);
        when(elevators[1].getAvailableCapacity()).thenReturn(100);
        when(elevators[0].getCalls()).thenReturn(calls);
        when(elevators[1].getCalls()).thenReturn(calls);
        assertThat(ElevatorPicker.getMostSuitableElevator(newCall, elevators).get(), equalTo(elevators[0]));
    }

    @Test
    void getMostSuitableElevator_BothUp_OneCallDirectionDown() {
        when(elevators[0].getElevatorDirection()).thenReturn(UP);
        when(elevators[1].getElevatorDirection()).thenReturn(UP);
        when(elevators[0].getCallDirection()).thenReturn(UP);
        when(elevators[1].getCallDirection()).thenReturn(DOWN);
        when(elevators[0].getCurrentFloor()).thenReturn(1);
        when(elevators[1].getCurrentFloor()).thenReturn(1);
        when(elevators[0].getAvailableCapacity()).thenReturn(100);
        when(elevators[1].getAvailableCapacity()).thenReturn(100);
        when(elevators[0].getCalls()).thenReturn(calls);
        when(elevators[1].getCalls()).thenReturn(calls);
        assertThat(ElevatorPicker.getMostSuitableElevator(newCall, elevators).get(), equalTo(elevators[0]));
    }

    @Test
    void getMostSuitableElevator_DownOnTheWay_vs_UpOnTheWay_bothCallDirectionsUp() {
        when(elevators[0].getElevatorDirection()).thenReturn(DOWN);
        when(elevators[1].getElevatorDirection()).thenReturn(UP);
        when(elevators[0].getCallDirection()).thenReturn(UP);
        when(elevators[1].getCallDirection()).thenReturn(UP);
        when(elevators[0].getCurrentFloor()).thenReturn(1);
        when(elevators[1].getCurrentFloor()).thenReturn(1);
        when(elevators[0].getAvailableCapacity()).thenReturn(100);
        when(elevators[1].getAvailableCapacity()).thenReturn(200);
        when(elevators[0].getCalls()).thenReturn(calls);
        when(elevators[1].getCalls()).thenReturn(calls);
        assertThat(ElevatorPicker.getMostSuitableElevator(newCall, elevators).get(), equalTo(elevators[1]));
    }

    @Test
    void getMostSuitableElevator_DownOnTheWay_vs_UpNotOnTheWay_bothCallDirectionsUp() {
        when(elevators[0].getElevatorDirection()).thenReturn(DOWN);
        when(elevators[1].getElevatorDirection()).thenReturn(UP);
        when(elevators[0].getCallDirection()).thenReturn(UP);
        when(elevators[1].getCallDirection()).thenReturn(UP);
        when(elevators[0].getCurrentFloor()).thenReturn(1);
        when(elevators[1].getCurrentFloor()).thenReturn(3);
        when(elevators[0].getAvailableCapacity()).thenReturn(100);
        when(elevators[1].getAvailableCapacity()).thenReturn(200);
        when(elevators[0].getCalls()).thenReturn(calls);
        when(elevators[1].getCalls()).thenReturn(calls);
        assertThat(ElevatorPicker.getMostSuitableElevator(newCall, elevators).get(), equalTo(elevators[0]));
    }

    @Test
    void getMostSuitableElevator_NoSuits() {
        when(elevators[0].getElevatorDirection()).thenReturn(UP);
        when(elevators[1].getElevatorDirection()).thenReturn(UP);
        when(elevators[0].getCallDirection()).thenReturn(UP);
        when(elevators[1].getCallDirection()).thenReturn(UP);
        when(elevators[0].getCurrentFloor()).thenReturn(3);
        when(elevators[1].getCurrentFloor()).thenReturn(3);
        when(elevators[0].getAvailableCapacity()).thenReturn(100);
        when(elevators[1].getAvailableCapacity()).thenReturn(200);
        when(elevators[0].getCalls()).thenReturn(calls);
        when(elevators[1].getCalls()).thenReturn(calls);
        assertThat(ElevatorPicker.getMostSuitableElevator(newCall, elevators).isPresent(), equalTo(false));
    }
}