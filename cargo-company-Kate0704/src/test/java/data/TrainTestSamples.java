package data;

import carriage.locomotive.Locomotive;
import lombok.experimental.UtilityClass;
import train.Train;

import static data.carriages.CoachCarTestSamples.coachCarWithNumber;
import static data.carriages.FreightCarTestSamples.freightCarWithCargo;
import static data.carriages.LocomotiveTestSamples.locomotiveWithDriver;

@UtilityClass
public class TrainTestSamples {
    public static Train emptyTrain() {
        Locomotive locomotive = locomotiveWithDriver();
        return new Train(locomotive);
    }

    public static Train trainWithCarriages() {
        Train train = emptyTrain();
        train.attachCarriageToBack(coachCarWithNumber(1));
        for (int i = 0; i < 3; i++) {
            train.attachCarriageToBack(freightCarWithCargo());
        }
        return train;
    }
}
