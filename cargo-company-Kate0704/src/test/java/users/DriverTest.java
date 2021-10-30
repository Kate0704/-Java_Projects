package users;

import data.UserTestSamples;
import org.junit.jupiter.api.Test;

import static data.UserTestSamples.adultDriver;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DriverTest {
    @Test
    void createValidDriver() {
        Driver driver = adultDriver();
        assertThat(driver, notNullValue());
    }


    @Test
    void createInvalidDriver() {
        assertThrows(IllegalArgumentException.class, UserTestSamples::notAdultDriver);
    }
}