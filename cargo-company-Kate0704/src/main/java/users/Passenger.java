package users;

import lombok.ToString;
import users.info.Ticket;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;


@Getter
@Slf4j
@ToString
public class Passenger extends User {
    private Ticket ticket;

    public Passenger(String name, String surname, int birthYear) {
        super(name, surname, birthYear);
    }

    public int buyTicket(@NotNull Ticket ticket) {
        this.ticket = ticket;
        log.debug("passenger {} bought ticket {}", this, ticket);
        return ticket.getTicketNumber();
    }
}
