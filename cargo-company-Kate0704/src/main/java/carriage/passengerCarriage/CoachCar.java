package carriage.passengerCarriage;

import carriage.Carriage;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import users.Passenger;
import users.info.Ticket;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@ToString
@Slf4j
public class CoachCar extends Carriage implements PassengerCarrying{
    private final Comfort comfort;
    private final int capacity;
    private final Passenger[] passengers;

    public CoachCar(Comfort comfort, int number) {
        super(number);
        this.comfort = comfort;
        this.capacity = determineCapacityByComfort();
        passengers = new Passenger[capacity];
    }

    public CoachCar(Comfort comfort, int number, int weight, int wheelDiameter, LocalDate manufactureDate) {
        super(number, weight, wheelDiameter, manufactureDate);
        this.comfort = comfort;
        this.capacity = determineCapacityByComfort();
        passengers = new Passenger[capacity];
    }

    public Passenger[] getPassengers() {
        return passengers.clone();
    }

    private int determineCapacityByComfort() {
        return switch (comfort) {
            case SEATED -> SEATED_CAR_CAPACITY;
            case RESERVED_SEATS -> RESERVED_SEATS_CAR_CAPACITY;
            case COUPE_CAR -> COUPE_CAR_CAPACITY;
        };
    }

    public void addPassenger(@NotNull Passenger passenger) {
        log.debug("add passenger with ticket {} to carriageNum={}, carriage={}", passenger, this.number, this);
        Ticket ticket = passenger.getTicket();
        validateTicket(ticket);
        log.debug("{} is valid", ticket);
        passengers[ticket.getPlace() - 1] = passenger;
        log.debug("{} was added to {} successfully", passenger, this);
    }

    public void validateTicket(@NotNull Ticket ticket) {
        int place = ticket.getPlace();
        checkArgument(place <= capacity && ticket.getPlace() > 0, "invalid place number");
        checkArgument(ticket.getCarriageNumber() == this.getNumber(), "invalid carriage number");
        checkArgument(passengers[place - 1] == null, "place " + place + " is already reserved");
    }

    public void clearOnArrival() {
        log.debug("clearing list of passengers ...");
        Arrays.fill(passengers, null);
    }

    public int getTicketsPriceSum() {
        log.debug("get tickets price sum for carriage={}", this);
        return Arrays.stream(passengers)
                .filter(Objects::nonNull)
                .map(Passenger::getTicket)
                .mapToInt(Ticket::getPrice)
                .sum();
    }

}
