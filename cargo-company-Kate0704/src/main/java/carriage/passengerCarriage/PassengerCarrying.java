package carriage.passengerCarriage;

import users.Passenger;
import users.info.Ticket;
import org.jetbrains.annotations.NotNull;

public interface PassengerCarrying {
    void addPassenger(@NotNull Passenger passenger);
    void validateTicket(@NotNull Ticket ticket);
    int getTicketsPriceSum();
}
