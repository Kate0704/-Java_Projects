package data.carriages;

import carriage.cargoCarriage.FreightCar;

import static data.CargoTestSamples.cargo_10;

public class FreightCarTestSamples {
    public static FreightCar anyFreightCar() {
        return new FreightCar();
    }

    public static FreightCar freightCarWithCargo() {
        FreightCar freightCar = new FreightCar();
        freightCar.addCargo(cargo_10());
        freightCar.addCargo(cargo_10());
        return freightCar;
    }
}
