package data;

import carriage.cargoCarriage.Cargo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CargoTestSamples {
    public static Cargo cargo_10() {
        return new Cargo("bag", 10);
    }

    public static Cargo cargo_20() {
        return new Cargo("bag", 20);
    }

    public static Cargo cargo_30() {
        return new Cargo("bag", 30);
    }

    public static Cargo anyCargo() {
        return cargo_10();
    }
    public static Cargo cargoOfWeight(int weight) {
        return new Cargo("bag", weight);
    }
}
