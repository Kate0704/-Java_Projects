package kate.zhevniak.algorithm;

import kate.zhevniak.domain.Elevator;
import kate.zhevniak.util.Call;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static kate.zhevniak.randomizer.PassengerRandomizer.MIN_WEIGHT;
import static kate.zhevniak.util.Direction.NO;
import static kate.zhevniak.util.Direction.UP;

public final class ElevatorPicker {
    public static Optional<Elevator> getMostSuitableElevator(Call call, Elevator[] elevators) {
        List<Elevator> suitableElevators = getSuitableElevators(call, elevators);
        Elevator elevator = getMinCostElevatorOf(suitableElevators);
        return Optional.ofNullable(elevator);
    }

    private static @Nullable Elevator getMinCostElevatorOf(@NotNull List<Elevator> elevators) {
        return elevators.isEmpty() ? null : elevators.stream()
                .min(Comparator.comparingInt(ElevatorPicker::calculateCost))
                .get();
    }

    private static List<Elevator> getSuitableElevators(Call call, Elevator[] elevators) {
        return Arrays.stream(elevators)
                .filter(elevator -> isSuitable(elevator, call))
                .toList();
    }

    private static boolean isSuitable(@NotNull Elevator elevator, @NotNull Call call) {
        boolean elevatorIsIdle = elevator.getElevatorDirection() == NO;
        boolean callDirectionsMatch = elevator.getCallDirection() == call.direction();
        boolean withoutPassengers = elevator.getElevatorDirection() != elevator.getCallDirection();
        boolean isOnTheWay = isOnTheWay(elevator, call.floor());
        boolean hasEnoughSpace = elevator.getAvailableCapacity() > MIN_WEIGHT;
        return elevatorIsIdle || (callDirectionsMatch && (withoutPassengers || isOnTheWay) && hasEnoughSpace);
    }

    private static boolean isOnTheWay(@NotNull Elevator elevator, int floor) {
        if (elevator.getElevatorDirection() == UP) return floor > elevator.getCurrentFloor();
        return floor < elevator.getCurrentFloor();
    }

    private static int calculateCost(@NotNull Elevator elevator) {
        return elevator.getCalls().size() * (elevator.getCapacity() - elevator.getAvailableCapacity());
    }
}
