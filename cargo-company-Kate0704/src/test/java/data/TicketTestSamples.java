package data;

import lombok.experimental.UtilityClass;
import users.info.Ticket;

import java.time.LocalDateTime;

@UtilityClass
public class TicketTestSamples {
    public static Ticket anyValidTicket() {
        return ticketWithCarNumber(7);
    }

    public static Ticket ticketWithCarNumber(int carNum) {
        return ticketWithCarNumberPlacePrice(carNum, 24, 16);
    }

    public static Ticket ticketWithPlace(int carNum, int place) {
        return ticketWithCarNumberPlacePrice(carNum, place, 16);
    }

    public static Ticket ticketWithPrice(int carNum, int price) {
        return ticketWithCarNumberPlacePrice(carNum, 24, price);
    }

    public static Ticket ticketWithCarNumberPlacePrice(int carNum, int place, int price) {
        return Ticket.builder()
                .from("Minsk")
                .to("Rechitsa")
                .departureDate(LocalDateTime.of(2021, 9, 29, 23, 54))
                .arrivalDate(LocalDateTime.of(2021, 9, 30, 7, 7))
                .carriageNumber(carNum)
                .place(place)
                .train("train_1")
                .price(price)
                .build();
    }

    public static Ticket ticketWithCarNumberAndPlace(int carNum, int place) {
        return ticketWithCarNumberPlacePrice(carNum, place, 16);
    }
}
