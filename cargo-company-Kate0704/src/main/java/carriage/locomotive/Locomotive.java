package carriage.locomotive;

import carriage.Carriage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import users.Driver;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@ToString
@Slf4j
public class Locomotive extends Carriage implements Drivable {
    private Driver driver;
    private final int traction;

    public Locomotive() {
        super(0);
        traction = DEFAULT_TRACTION;
    }

    public Locomotive(int number, int weight, int wheelDiameter, LocalDate manufactureDate) {
        this(number, weight, wheelDiameter, manufactureDate, DEFAULT_TRACTION);
    }

    public Locomotive(int number, int weight, int wheelDiameter, LocalDate manufactureDate, int traction) {
        super(number, weight, wheelDiameter, manufactureDate);
        this.traction = traction;
    }

    public void setDriver(@NotNull Driver driver) {
        checkForLicence(driver);
        log.debug("{} becomes a driver of {}", driver, this);
        this.driver = driver;
    }

    public String getVerboseInfo() {
        String carriageStringRepr = super.toString();
        carriageStringRepr = StringUtils.chop(carriageStringRepr);
        return carriageStringRepr + ", driver = " + driver + ", traction = " + traction + ")";
    }

    @Override
    public void clearOnArrival() {
        driver = null;
    }
}
