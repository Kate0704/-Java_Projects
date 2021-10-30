package persistence.access;

import domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static data.UserTestSamples.anyValidUser;
import static domain.user.info.UserRole.ADMIN;
import static domain.user.info.UserRole.USER;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class NotAdminDenyTest {
    private User user;
    AccessDenyRule accessChecker;

    @BeforeEach
    void before() {
        user = anyValidUser();
        accessChecker = new NotAdminDeny();
    }

    @Test
    void isDeny() {
        user.setRole(USER);
        assertThat(accessChecker.isDeny(user), is(true));
    }

    @Test
    void isNotDeny() {
        user.setRole(ADMIN);
        assertThat(accessChecker.isDeny(user), is(false));
    }
}