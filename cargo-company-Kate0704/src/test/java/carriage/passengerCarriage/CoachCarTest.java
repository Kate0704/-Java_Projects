package carriage.passengerCarriage;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.Passenger;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import static data.TicketTestSamples.ticketWithCarNumberAndPlace;
import static data.UserTestSamples.anyValidPassenger;
import static data.carriages.CoachCarTestSamples.anyCoachCar;
import static data.carriages.CoachCarTestSamples.coachCarWithPassengers;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoachCarTest {
    private CoachCar coachCar;
    private CoachCar coachCarWithPassengers;
    private Passenger passenger;

    @BeforeEach
    void setUp() {
        coachCar = anyCoachCar();
        coachCarWithPassengers = coachCarWithPassengers();
        passenger = anyValidPassenger();
    }

    @Test
    void getPassengers() {
        Passenger[] passengers = coachCarWithPassengers.getPassengers();
        int nullIndex = ArrayUtils.indexOf(passengers, null);
        passengers[nullIndex] = anyValidPassenger();
        assertThat(coachCarWithPassengers.getPassengers()[nullIndex],  nullValue());
    }

    @Test
    void addPassenger() {
        int place = 1;
        passenger.buyTicket(ticketWithCarNumberAndPlace(coachCar.getNumber(), place));
        coachCar.addPassenger(passenger);
        assertThat(coachCar.getPassengers()[place - 1], equalTo(passenger));
    }

    @Test
    void validateInvalidTickets() {
        int place = 1;
        validateTicketWithInvalidCarriageNumber(place);
        validateTwoEqualTickets(place);
        validateTicketWithInvalidPlace(place);
    }

    void validateTicketWithInvalidCarriageNumber(int place) {
        assertThrows(IllegalArgumentException.class,
                () -> coachCar.validateTicket(ticketWithCarNumberAndPlace(coachCar.getNumber() + 1, place)));
    }

    void validateTwoEqualTickets(int place) {
        passenger.buyTicket(ticketWithCarNumberAndPlace(coachCar.getNumber(), place));
        coachCar.addPassenger(passenger);
        assertThrows(IllegalArgumentException.class, () -> coachCar.addPassenger(passenger));
    }

    void validateTicketWithInvalidPlace(int place) {
        int invalidPlace = coachCar.getCapacity() + 1;
        passenger.buyTicket(ticketWithCarNumberAndPlace(coachCar.getNumber(), invalidPlace));
        assertThrows(IllegalArgumentException.class, () -> coachCar.addPassenger(passenger));
    }

    @Test
    void clearOnArrival() {
        coachCarWithPassengers.clearOnArrival();
        assertThat(Arrays.stream(coachCar.getPassengers()).anyMatch(Objects::nonNull), is(false));
    }

    @Test
    void getTicketsPriceSum() {
        assertThat(coachCarWithPassengers.getTicketsPriceSum(), equalTo(36));
    }

    @Test
    void testConstructors() {
        coachCar = new CoachCar(Comfort.COUPE_CAR, 1, 100, 50, LocalDate.now());
        assertThat(coachCar, instanceOf(CoachCar.class));

        coachCar = new CoachCar(Comfort.SEATED, 1);
        assertThat(coachCar, instanceOf(CoachCar.class));
    }
}