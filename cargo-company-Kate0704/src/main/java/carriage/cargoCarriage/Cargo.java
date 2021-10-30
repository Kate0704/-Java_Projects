package carriage.cargoCarriage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@ToString
@EqualsAndHashCode
public class Cargo {
    private static int currentId = 0;
    private final int id;
    private final String name;
    private final int weight;

    public Cargo(@NotNull String name, int weight) {
        this.id = currentId++;
        this.name = name;
        checkArgument(weight > 0, "weight must be a positive value");
        this.weight = weight;
    }
}
