package data;

import domain.user.User;

public class UserTestSamples {
    public static User anyValidUser() {
        return new User("ivanov");
    }

    public static User anyUser() {
        return anyValidUser();
    }
}
