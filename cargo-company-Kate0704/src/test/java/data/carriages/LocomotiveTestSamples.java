package data.carriages;

import carriage.locomotive.Locomotive;

import static data.UserTestSamples.driverWithLicence;

public class LocomotiveTestSamples {
    public static Locomotive anyLocomotive() {
        return new Locomotive();
    }

    public static Locomotive locomotiveWithDriver() {
        Locomotive locomotive = new Locomotive();
        locomotive.setDriver(driverWithLicence());
        return locomotive;
    }
}
