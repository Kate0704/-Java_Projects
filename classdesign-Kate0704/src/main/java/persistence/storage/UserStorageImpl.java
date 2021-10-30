package persistence.storage;

import domain.user.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class UserStorageImpl implements UserStorage {

    @Override
    public Optional<User> findByUserName(String userName) {
        log.debug("findByUserName(userName={})", userName);
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public String persist(User user) {
        log.debug("persist(user={})", user);
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Optional<User> load(String userId) {
        log.debug("load(userId={})", userId);
        throw new UnsupportedOperationException("not implemented yet");
    }
}
