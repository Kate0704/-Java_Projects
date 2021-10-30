package kate.zhevniak.statistics;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Statistics {
    private int transportedPassengersAmount;
    private int passedFloorsAmount;
    private int numberOfCalls;
    private final int[] fromFloor;
    private final int[] toFloor;

    public Statistics(int numberOfFloors) {
        transportedPassengersAmount = 0;
        passedFloorsAmount = 0;
        numberOfCalls = 0;
        fromFloor = new int[numberOfFloors];
        toFloor = new int[numberOfFloors];
    }

    public void addTransportedPassengersAmount(int amount) {
        transportedPassengersAmount += amount;
    }

    public void addFromFloor(int floor, int amount) {
        fromFloor[floor] += amount;
    }

    public void addToFloor(int floor, int amount) {
        toFloor[floor] += amount;
    }

    public void incrementPassedFloors() {
        passedFloorsAmount++;
    }

    public void incrementCallsAmount() {
        numberOfCalls++;
    }

    public List<Integer>  getFromFloorTop() {
        return getTop(fromFloor);
    }

    public List<Integer>  getToFloorTop() {
        return getTop(toFloor);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private @NotNull List<Integer> getTop(int[] floorsStat) {
        int maxVal = Arrays.stream(floorsStat).max().getAsInt();
        List<Integer> topFloors = new ArrayList<>();
        if (maxVal != 0) {
            for (int i = 0; i < floorsStat.length; i++) {
                if (floorsStat[i] == maxVal) {
                    topFloors.add(i);
                }
            }
        }
        return topFloors;
    }

}
