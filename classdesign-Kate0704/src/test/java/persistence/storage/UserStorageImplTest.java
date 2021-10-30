package persistence.storage;

import domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static data.UserTestSamples.anyValidUser;

class UserStorageImplTest {
    private UserStorage userStorage;

    @BeforeEach
    private void before() {
        userStorage = new UserStorageImpl();
    }

    @Test
    void findByUserName() {
        User user = anyValidUser();
        String username = "petrov";
        user.setUsername(username);

        userStorage.persist(user);
        Optional<User> userOptional = userStorage.findByUserName(username);

        assertThat(userOptional.isPresent(), is(true));
        assertThat(userOptional.get(), is(equalTo(user)));
    }

    @Test
    void findByUserName_null() {
        assertThrows(NoSuchElementException.class, () -> userStorage.findByUserName(null));
    }

    @Test
    void persist() {
        User user = anyValidUser();

        final String id = userStorage.persist(user);

        assertThat(id, is(notNullValue()));
        Optional<User> loaded = userStorage.load(id);

        assertThat(loaded.isPresent(), is(true));
        assertThat(loaded.get(), is(equalTo(user)));
    }

    @Test
    public void persist_null() {
        assertThrows(NullPointerException.class, () -> userStorage.persist(null));
    }

    @Test
    public void load_null() {
        assertThrows(NoSuchElementException.class, () -> userStorage.load(null));
    }

}
