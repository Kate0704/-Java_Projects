package persistence.access;

import domain.user.User;

public interface AccessDenyRule {
    boolean isDeny(User user);
}
