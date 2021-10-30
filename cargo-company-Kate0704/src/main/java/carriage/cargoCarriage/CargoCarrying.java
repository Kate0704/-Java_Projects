package carriage.cargoCarriage;

import org.jetbrains.annotations.NotNull;

public interface CargoCarrying {
    int getTotalCargoWeight();
    boolean removeCargo(@NotNull Cargo cargo);
}
