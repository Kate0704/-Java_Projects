package carriage.cargoCarriage;

import carriage.Carriage;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
@Slf4j
public class FreightCar extends Carriage implements CargoCarrying{
    private final int maxCapacity;
    private int availableCapacity;
    private final List<Cargo> cargos;

    public FreightCar(int maxCapacity) {
        this.maxCapacity = this.availableCapacity = maxCapacity;
        cargos = new ArrayList<>();
    }

    public FreightCar() {
        this(DEFAULT_CARGO_CARRIAGE_CAPACITY);
    }

    public List<Cargo> getCargos() {
        return Collections.unmodifiableList(cargos);
    }

    public int getTotalCargoWeight() {
        return cargos.stream()
                .mapToInt(Cargo::getWeight)
                .sum();
    }

    public boolean addCargo(Cargo cargo) {
        log.debug("try adding {} to {}", cargo, this);
        boolean available = (cargo.getWeight() <= availableCapacity);
        if (available) {
            cargos.add(cargo);
            availableCapacity -= cargo.getWeight();
        }
        log.debug("available capacity isn't enough");
        return available;
    }

    public boolean removeCargo(@NotNull Cargo cargo) {
        boolean isPresent = cargos.contains(cargo);
        if (isPresent) {
            cargos.remove(cargo);
            availableCapacity += cargo.getWeight();
            log.debug("remove {} from {}", cargo, this);
        }
        log.debug("{} is not present in {}", cargo, this);
        return isPresent;
    }

    public void clearOnArrival() {
        cargos.clear();
        availableCapacity = maxCapacity;
        log.debug("remove all cargos");
    }
}
