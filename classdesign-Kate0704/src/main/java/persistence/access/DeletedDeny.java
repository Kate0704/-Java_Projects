package persistence.access;

import domain.user.User;
import domain.user.info.UserStatus;

public class DeletedDeny implements AccessDenyRule {
    @Override
    public boolean isDeny(User user) {
        return user.getStatus() == UserStatus.DELETED;
    }
}
