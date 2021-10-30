package carriage;

import lombok.*;
import train.Cleanable;

import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(exclude = {"weight", "wheelDiameter"})
@EqualsAndHashCode
public abstract class Carriage implements DefaultCarriageCharacteristics, Cleanable {
    protected int id;
    protected final int number;
    protected final int weight;
    protected final int wheelDiameter;
    protected final LocalDate manufactureDate;
    protected Carriage nextCarriage;
    protected Carriage prevCarriage;

    public Carriage(int number) {
        this.number = number;
        weight = DEFAULT_WEIGHT;
        wheelDiameter = DEFAULT_WHEEL_DIAMETER;
        manufactureDate = CURRENT_DATE;
        nextCarriage = null;
    }

    public Carriage() {
        this(0);
    }
}