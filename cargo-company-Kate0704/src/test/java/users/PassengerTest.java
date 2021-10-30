package users;

import data.UserTestSamples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import users.info.Ticket;

import static data.TicketTestSamples.anyValidTicket;
import static data.UserTestSamples.anyValidPassenger;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PassengerTest {
    private Passenger passenger;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        passenger = anyValidPassenger();
        ticket = anyValidTicket();
    }

    @Test
    void createInvalidUser() {
        assertThrows(IllegalArgumentException.class, UserTestSamples::anyInvalidUser);
    }

    @Test
    void buyTicket() {
        int ticketNumber = passenger.buyTicket(ticket);
        assertThat(ticket.getTicketNumber(), equalTo(ticketNumber));
        assertThat(passenger.getTicket(), equalTo(ticket));
    }

    @Test
    void setId() {
        passenger.setId(1);
        assertThat(passenger.getId(), equalTo(1));
    }
}