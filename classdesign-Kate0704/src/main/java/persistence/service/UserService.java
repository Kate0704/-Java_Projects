package persistence.service;

import domain.user.User;

import java.util.Optional;

public interface UserService {
    String createUser(User user);

    Optional<User> findById(String id);
}
