package carriage.cargoCarriage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static data.CargoTestSamples.*;
import static data.carriages.FreightCarTestSamples.anyFreightCar;
import static data.carriages.FreightCarTestSamples.freightCarWithCargo;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FreightCarTest {
    private FreightCar freightCar;
    private FreightCar freightCarWithCargo;
    private Cargo cargo;

    @BeforeEach
    void setUp() {
        cargo = anyCargo();
        freightCar = anyFreightCar();
        freightCarWithCargo = freightCarWithCargo();
    }

    @Test
    void getCargos() {
        assertThrows(UnsupportedOperationException.class, () -> freightCar.getCargos().add(cargo));
    }

    @Test
    void getTotalCargoWeight() {
        freightCar.addCargo(cargo_10());
        freightCar.addCargo(cargo_20());
        freightCar.addCargo(cargo_30());
        assertThat(freightCar.getTotalCargoWeight(), equalTo(60));
        freightCar.clearOnArrival();
        assertThat(freightCar.getTotalCargoWeight(), equalTo(0));
    }

    @Test
    void addCargo() {
        Cargo cargo = cargo_10();
        assertThat(freightCar.addCargo(cargo), is(true));
        assertThat(freightCar.getCargos().contains(cargo), is(true));
    }

    @Test
    void addCargo_NoAvailableSpace() {
        Cargo cargo = cargoOfWeight(100);
        assertThat(freightCar.addCargo(cargo), is(false));
    }

    @Test
    void removeCargo() {
        Cargo cargo = freightCarWithCargo.getCargos().get(0);
        freightCarWithCargo.removeCargo(cargo);
        assertThat(freightCarWithCargo.getCargos().contains(cargo), is(false));
    }

    @Test
    void removeNotExistingCargo() {
        Cargo cargo = cargo_30();
        freightCarWithCargo.removeCargo(cargo);
        assertThat(freightCarWithCargo.getCargos().contains(cargo), is(false));
    }

    @Test
    void clearOnArrival() {
        freightCarWithCargo.clearOnArrival();
        assertThat(freightCarWithCargo.getCargos().isEmpty(), is(true));
    }
}