package persistence.access;

import domain.user.User;
import domain.user.info.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static data.UserTestSamples.anyValidUser;
import static domain.user.info.UserStatus.ACTIVE;
import static domain.user.info.UserStatus.DELETED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class DeletedDenyTest {
    private User user;
    AccessDenyRule accessChecker;

    @BeforeEach
    void before() {
        user = anyValidUser();
        accessChecker = new DeletedDeny();
    }

    @Test
    void isDeny() {
        user.setStatus(DELETED);
        assertThat(accessChecker.isDeny(user), is(true));
    }

    @Test
    void isNotDeny() {
        user.setStatus(ACTIVE);
        assertThat(accessChecker.isDeny(user), is(false));
        user.setStatus(UserStatus.INITIAL);
        assertThat(accessChecker.isDeny(user), is(false));
    }
}