package train;

import carriage.Carriage;
import carriage.cargoCarriage.Cargo;
import carriage.cargoCarriage.FreightCar;
import carriage.locomotive.Locomotive;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@Slf4j
public class Train implements Cleanable {
    private final Locomotive locomotive;
    private int carriagesCount;

    public Train(@NotNull Locomotive locomotive) {
        this.locomotive = locomotive;
        carriagesCount = 1;
    }

    public void attachCarriageByIndex(@NotNull Carriage carriage, int index) {
        log.debug("attach carriage {} by index {}", carriage, index);
        checkArgument(index <= carriagesCount && index > 0, "invalid index");
        checkArgument(carriage.getPrevCarriage() == null && carriage.getNextCarriage() == null,
                "carrige has already been attached to the train");
        log.debug("carriage and index are valid");
        Carriage prev = locomotive;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.getNextCarriage();
        }
        log.debug("prev carriage: {}", prev);
        Carriage next = prev.getNextCarriage();
        log.debug("next carriage: {}", next);
        prev.setNextCarriage(carriage);
        carriage.setNextCarriage(next);
        carriage.setPrevCarriage(prev);
        carriagesCount++;
        log.debug("carriages count = {}", carriagesCount);
    }

    public void attachCarriageToBack(@NotNull Carriage carriage) {
        attachCarriageByIndex(carriage, carriagesCount);
    }

    public void removeCarriageByIndex(int index) {
        log.debug("remove carriage by index {}", index);
        checkArgument(index <= carriagesCount && index > 0, "invalid index");
        log.debug("index is valid");
        Carriage curr = locomotive;
        for (int i = 0; i <= index - 1; i++) {
            curr = curr.getNextCarriage();
        }
        Carriage prev = curr.getPrevCarriage();
        prev.setNextCarriage(curr.getNextCarriage());
        log.debug("prev carriage: {}", prev);
        Carriage next = prev.getNextCarriage();
        log.debug("next carriage: {}", next);
        if (next != null)
            next.setPrevCarriage(prev);
        curr.setNextCarriage(null);
        curr.setPrevCarriage(null);
        carriagesCount--;
        log.debug("carriages count = {}", carriagesCount);
    }

    public void removeCarriageFromBack() {
        removeCarriageByIndex(carriagesCount - 1);
    }

    public int addCargo(@NotNull Cargo cargo) {
        log.debug("add {} to {}", cargo, this);
        Carriage carriage = locomotive.getNextCarriage();
        for (int i = 1; carriage != null; i++) {
            if (carriage instanceof FreightCar && ((FreightCar) carriage).addCargo(cargo)) {
                log.debug("add to carriage with index {}", i);
                return i;
            }
            carriage = carriage.getNextCarriage();
        }
        return redistributeCargos(cargo);
    }

    private int redistributeCargos(Cargo cargo) {
        log.debug("redistribute cargos");
        throw new UnsupportedOperationException("not implemented yet");
    }

    public void clearOnArrival() {
        log.debug("clear on arrival {} on arrival", this);
        Carriage carriage = locomotive.getNextCarriage();
        while (carriage != null) {
            carriage.clearOnArrival();
            carriage = carriage.getNextCarriage();
        }
    }

    @Override
    public String toString() {
        Carriage current = locomotive;
        var result = new StringBuilder("Train(locomotive=" + locomotive + ", ");
        for (int i = 0; i < carriagesCount - 1; i++) {
            current = current.getNextCarriage();
            result.append(current).append(", ");
        }
        final Carriage curr = locomotive;
        return result.append(")").toString();
    }
}
