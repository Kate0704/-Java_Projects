package persistence.storage;

import domain.user.User;

import java.util.Optional;

public interface UserStorage {
    Optional<User> findByUserName(String userName);

    String persist(User user);

    Optional<User> load(String id);
}
