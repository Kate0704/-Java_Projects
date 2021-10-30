package data.carriages;

import carriage.passengerCarriage.CoachCar;
import users.Passenger;

import static carriage.passengerCarriage.Comfort.RESERVED_SEATS;
import static data.TicketTestSamples.ticketWithCarNumberPlacePrice;
import static data.UserTestSamples.anyValidPassenger;
import static data.UserTestSamples.passengerWithAnyTicket;

public class CoachCarTestSamples {
    public static CoachCar anyCoachCar() {
        return coachCarWithNumber(7);
    }

    public static CoachCar coachCarWithNumber(int number) {
        return new CoachCar(RESERVED_SEATS, number);
    }

    public static CoachCar coachCarWithPassenger() {
        CoachCar coachCar = anyCoachCar();
        coachCar.addPassenger(passengerWithAnyTicket());
        return coachCar;
    }

    public static CoachCar coachCarWithPassengers() {
        int carNum = 1;
        CoachCar coachCar = coachCarWithNumber(carNum);

        Passenger passenger1 = anyValidPassenger();
        passenger1.buyTicket(ticketWithCarNumberPlacePrice(carNum, 10, 10));
        coachCar.addPassenger(passenger1);
        Passenger passenger2 = anyValidPassenger();
        passenger2.buyTicket(ticketWithCarNumberPlacePrice(carNum, 5, 10));
        coachCar.addPassenger(passenger2);
        Passenger passenger3 = anyValidPassenger();
        passenger3.buyTicket(ticketWithCarNumberPlacePrice(carNum, 15, 16));
        coachCar.addPassenger(passenger3);

        return coachCar;
    }
}
