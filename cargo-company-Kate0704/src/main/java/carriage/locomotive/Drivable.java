package carriage.locomotive;

import users.Driver;

import static com.google.common.base.Preconditions.checkArgument;

public interface Drivable {
    default void checkForLicence(Driver driver) {
        checkArgument(driver.hasLicence(), "driver must have a licence");
    }
}
