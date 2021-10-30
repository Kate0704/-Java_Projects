package validation;

import domain.user.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserValidator {

    public boolean validateForCreation(User user) {
        log.debug("validateForCreation({})", user);
        return (user.getUsername() != null) &&
                (user.getSurname() != null) &&
                (user.getName() != null) &&
                (user.getAddress() != null);
    }
}
