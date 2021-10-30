package persistence.service;

import domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import persistence.storage.UserStorage;
import validation.UserValidator;

import java.util.Optional;

import static domain.user.info.UserStatus.ACTIVE;

@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;
    private final UserValidator userValidator;

    @Override
    public String createUser(User user) {
        log.debug("createUser({})", user);
        final boolean valid = userValidator.validateForCreation(user);
        if (!valid) {
            log.debug("user is invalid");
            throw new IllegalArgumentException("user is not valid" + user);
        }
        log.debug("user is valid");

        if (userStorage.findByUserName(user.getUsername()).isPresent()){
            log.debug("username is busy");
            throw new IllegalArgumentException("username is busy");
        }
        log.debug("username is available");

        user.setStatus(ACTIVE);
        log.debug("set user status to active");
        final String id = userStorage.persist(user);
        user.setId(id);
        log.debug("user id is " + id);

        return id;
    }

    @Override
    public Optional<User> findById(String id) {
        log.debug("findById(id={})", id);
        return userStorage.load(id);
    }
}
