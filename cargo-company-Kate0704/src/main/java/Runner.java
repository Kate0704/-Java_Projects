import carriage.cargoCarriage.Cargo;
import carriage.cargoCarriage.FreightCar;
import carriage.locomotive.Locomotive;
import carriage.passengerCarriage.CoachCar;
import carriage.passengerCarriage.Comfort;
import lombok.extern.slf4j.Slf4j;
import train.Train;
import users.Driver;
import users.Passenger;
import users.info.Ticket;

import java.time.LocalDateTime;

@Slf4j
public class Runner {
    public static void main(String[] args) {
        log.debug("\n# " + "-".repeat(70));
        Passenger passenger = new Passenger("Katerina", "Zhevniak", 2001);
        Locomotive locomotive = new Locomotive(2);

        Driver driver = new Driver("Katerina", "Zhevniak", 2001);
        driver.earnLicence();
        locomotive.setDriver(driver);
        System.out.println(locomotive);
        System.out.println(locomotive.getVerboseInfo());
        CoachCar coachCar = new CoachCar(Comfort.COUPE_CAR, 1);
        Ticket ticket = Ticket.builder()
                .from("Minsk")
                .to("Rechitsa")
                .departureDate(LocalDateTime.of(2021, 9, 29, 23, 54))
                .arrivalDate(LocalDateTime.of(2021, 9, 30, 7, 7))
                .carriageNumber(coachCar.getNumber())
                .place(4)
                .train("train_1")
                .price(16)
                .build();
        int ticketId = passenger.buyTicket(ticket);
        coachCar.addPassenger(passenger);
        System.out.println(coachCar);
        FreightCar freightCar1 = new FreightCar(40);
        FreightCar freightCar2 = new FreightCar(50);
        FreightCar freightCar3 = new FreightCar(60);
        FreightCar freightCar4 = new FreightCar(70);
        System.out.println(freightCar1);
        System.out.println(freightCar2);
        Cargo cargo = new Cargo("name", 5);
        Train train = new Train(locomotive);
        train.attachCarriageToBack(coachCar);
        train.attachCarriageByIndex(freightCar1, 1);
        train.attachCarriageByIndex(freightCar2, 2);
        train.attachCarriageToBack(freightCar3);
        train.attachCarriageToBack(freightCar4);
        train.attachCarriageByIndex(new CoachCar(Comfort.COUPE_CAR, 4), 3);
        train.removeCarriageByIndex(3);
        train.removeCarriageByIndex(1);
        train.removeCarriageFromBack();

        System.out.println(train);

        //freightCar1.getCargos().add(2, new Cargo("bag", 3));
//        Locomotive locomotive2 = train.getLocomotive();
//        Driver newDriver = new Driver("Changed", "name", 1980);
//        newDriver.earnLicence();
//        locomotive2.setDriver(newDriver);
//        System.out.println(locomotive);
//        System.out.println(locomotive2);
//
//        int[] weights = {30, 30, 30, 30, 30, 30, 20, 10, 10, 10};
//        int carIndex = 0;
//        for (int weight: weights) {
//            carIndex = train.addCargo(new Cargo("bag", weight));
//            System.out.println(carIndex);
//        }
    }
}
