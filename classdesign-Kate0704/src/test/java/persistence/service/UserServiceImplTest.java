package persistence.service;

import domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.storage.UserStorage;
import persistence.storage.UserStorageImpl;
import validation.UserValidator;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static data.UserTestSamples.anyUser;

class UserServiceImplTest {
    private UserService userService;
    private UserValidator userValidator;
    private UserStorage userStorage;

    @BeforeEach
    void createUser() {
        userValidator = mock(UserValidator.class);
        userStorage = mock(UserStorageImpl.class);

        userService = new UserServiceImpl(userStorage, userValidator);
    }

    @Test
    void createUser_invalid() {
        User user = anyUser();

        when(userValidator.validateForCreation(user)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
    }

    @Test
    void createUser_usernameIsBusy() {
        String username = "busy_username";
        User user = anyUser();
        user.setUsername(username);
        when(userStorage.findByUserName(username)).thenReturn(Optional.of(user));

        when(userValidator.validateForCreation(user)).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
    }
}