package users;

import lombok.extern.slf4j.Slf4j;

import static com.google.common.base.Preconditions.checkArgument;
import static users.info.UserType.ADULT;

@Slf4j
public class Driver extends User {
    private boolean licence;

    public Driver(String name, String surname, int birthYear) {
        super(name, surname, birthYear);
        checkArgument(userType == ADULT, "driver must be an adult");
        licence = false;
    }

    public void earnLicence() {
        licence = true;
        log.debug("driver {} earned licence", this);
    }

    public boolean hasLicence() {
        return licence;
    }
}
