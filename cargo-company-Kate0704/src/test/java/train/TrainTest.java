package train;

import carriage.Carriage;
import carriage.cargoCarriage.Cargo;
import carriage.cargoCarriage.FreightCar;
import carriage.passengerCarriage.CoachCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static data.CargoTestSamples.anyCargo;
import static data.CargoTestSamples.cargoOfWeight;
import static data.TrainTestSamples.trainWithCarriages;
import static data.carriages.CoachCarTestSamples.coachCarWithNumber;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TrainTest {
    private Train train;
    private CoachCar carriage;

    @BeforeEach
    void setUp() {
        train = trainWithCarriages();
        carriage = coachCarWithNumber(2);
    }

    @Test
    void attachCarriageByIndex() {
        Carriage next = train.getLocomotive().getNextCarriage();
        train.attachCarriageByIndex(carriage, 1);
        assertThat(train.getLocomotive().getNextCarriage(), equalTo(carriage));
        assertThat(carriage.getNextCarriage(), equalTo(next));
        assertThat(carriage.getPrevCarriage(), equalTo(train.getLocomotive()));
    }

    @Test
    void attachCarriageByInvalidIndex() {
        assertThrows(IllegalArgumentException.class, () -> train.attachCarriageByIndex(carriage, 0));
        assertThrows(IllegalArgumentException.class, () -> train.attachCarriageByIndex(carriage, 10));
    }

    @Test
    void doubleAttachCarriageByIndex() {
        train.attachCarriageByIndex(carriage, 1);
        assertThrows(IllegalArgumentException.class, () -> train.attachCarriageByIndex(carriage, 2));
    }

    @Test
    void attachCarriageToBack() {
        train.attachCarriageToBack(carriage);
        Carriage lastCarriage = train.getLocomotive();
        while(lastCarriage.getNextCarriage() != null) {
            lastCarriage = lastCarriage.getNextCarriage();
        }
        assertThat((CoachCar) lastCarriage, equalTo(carriage));
    }

    @Test
    void removeCarriageByIndex() {
        Carriage curr = train.getLocomotive().getNextCarriage();
        Carriage next = train.getLocomotive().getNextCarriage().getNextCarriage();
        train.removeCarriageByIndex(1);
        assertThat(train.getLocomotive().getNextCarriage(), equalTo(next));
        assertThat(curr.getNextCarriage(), equalTo(null));
        assertThat(curr.getPrevCarriage(), equalTo(null));
        assertThat(next.getPrevCarriage(), equalTo(train.getLocomotive()));
    }

    @Test
    void removeCarriageFromBack() {
        int carriagesCount = train.getCarriagesCount();
        Carriage lastCarriage = train.getLocomotive();
        while(lastCarriage.getNextCarriage() != null) {
            lastCarriage = lastCarriage.getNextCarriage();
        }
        Carriage prev = lastCarriage.getPrevCarriage();

        train.removeCarriageFromBack();
        assertThat(train.getCarriagesCount(), is(equalTo(carriagesCount - 1)));
        assertThat(prev.getNextCarriage(), equalTo(null));
        assertThat(lastCarriage.getNextCarriage(), is(equalTo(null)));
        assertThat(lastCarriage.getPrevCarriage(), is(equalTo(null)));
    }

    @Test
    void addCargo() {
        Cargo cargo = anyCargo();
        int index = train.addCargo(cargo);
        boolean validIndex = index > 0;
        assertThat(validIndex, is(true));

        Carriage carriage = train.getLocomotive();
        for (int i = 1; i <= index; i++) {
            carriage = carriage.getNextCarriage();
        }
        assertInstanceOf(FreightCar.class, carriage);

        assertThat(((FreightCar)carriage).getCargos().contains(cargo), is(true));
    }

    @Test
    void addTooBigCargo() {
        Cargo cargo = cargoOfWeight(1000);
        assertThrows(UnsupportedOperationException.class, () -> train.addCargo(cargo));
    }

    @Test
    void clearOnArrival() {
        train.clearOnArrival();
        Carriage carriage = train.getLocomotive();
        Carriage carriageCopy;
        while (carriage.getNextCarriage() != null) {
            carriageCopy = carriage;
            carriage.clearOnArrival();
            assertThat(carriage, equalTo(carriageCopy));
            carriage = carriage.getNextCarriage();
        }
    }

    @Test
    void carriagesCount() {
        Carriage carriage = train.getLocomotive();
        int count = 1;
        while (carriage.getNextCarriage() != null) {
            count++;
            carriage = carriage.getNextCarriage();
        }
        assertThat(count, equalTo(train.getCarriagesCount()));
    }

}