package data;

import lombok.experimental.UtilityClass;
import users.Driver;
import users.Passenger;
import users.User;

import static data.TicketTestSamples.anyValidTicket;

@UtilityClass
public class UserTestSamples {
    public static User anyInvalidUser() {
        return new Passenger("Katerina", "Zhevniak", 2050);
    }

    public static Passenger anyValidPassenger() {
        return new Passenger("Katerina", "Zhevniak", 2001);
    }

    public static Passenger passengerWithAnyTicket() {
        Passenger passenger = anyValidPassenger();
        passenger.buyTicket(anyValidTicket());
        return passenger;
    }

    public static Driver notAdultDriver() {
        return new Driver("Anastasia", "Zhevniak", 2010);
    }

    public static Driver adultDriver() {
        return new Driver("Victor", "Zhevniak", 1975);
    }

    public static Driver driverWithLicence() {
        Driver driver = adultDriver();
        driver.earnLicence();
        return driver;
    }
}
