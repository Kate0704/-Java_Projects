package carriage;

import java.time.LocalDate;

public interface DefaultCarriageCharacteristics {
    int RANDOM_NUMBER = (int) (Math.random() * 40);
    int DEFAULT_WEIGHT = 30;
    int DEFAULT_WHEEL_DIAMETER = 50;
    LocalDate CURRENT_DATE = LocalDate.now();
    int DEFAULT_TRACTION = 300;
    int SEATED_CAR_CAPACITY = 62;                       // max number of people
    int RESERVED_SEATS_CAR_CAPACITY = 54;
    int COUPE_CAR_CAPACITY = 36;
    int DEFAULT_CARGO_CARRIAGE_CAPACITY = 80;           // in tons

}
