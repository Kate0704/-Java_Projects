package carriage.locomotive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.Driver;

import java.time.LocalDate;

import static data.UserTestSamples.adultDriver;
import static data.carriages.LocomotiveTestSamples.anyLocomotive;
import static data.carriages.LocomotiveTestSamples.locomotiveWithDriver;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocomotiveTest {
    private Locomotive locomotive;
    private Driver driver;

    @BeforeEach
    void setUp() {
        locomotive = anyLocomotive();
        driver = adultDriver();
    }

    @Test
    void setDriver() {
        driver.earnLicence();
        locomotive.setDriver(driver);
        assertThat(locomotive.getDriver(), equalTo(driver));
    }

    @Test
    void setDriverWithoutLicence() {
        assertThrows(IllegalArgumentException.class, () -> locomotive.setDriver(driver));
    }

    @Test
    void clearOnArrival() {
        locomotive = locomotiveWithDriver();
        locomotive.clearOnArrival();
        assertThat(locomotive.getDriver(), nullValue());
    }

    @Test
    void getVerboseInfo() {
        String verbose = String.format("Carriage(id=0, number=0, manufactureDate=%s, nextCarriage=null, prevCarriage=null, driver = null, traction = 300)", LocalDate.now());
        assertThat(locomotive.getVerboseInfo(), equalTo(verbose));
    }

    @Test
    void testConstructors() {
        locomotive = new Locomotive(20);
        assertThat(locomotive, instanceOf(Locomotive.class));

        locomotive = new Locomotive(1, 100, 50, LocalDate.now());
        assertThat(locomotive, instanceOf(Locomotive.class));

        locomotive = new Locomotive(1, 100, 50, LocalDate.now(), 20);
        assertThat(locomotive, instanceOf(Locomotive.class));

        assertThat(locomotive.getWeight(), is(equalTo(100)));
        assertThat(locomotive.getWheelDiameter(), is(equalTo(50)));
    }

}