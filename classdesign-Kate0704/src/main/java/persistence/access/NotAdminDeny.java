package persistence.access;

import domain.user.User;
import domain.user.info.UserRole;

public class NotAdminDeny implements AccessDenyRule {
    @Override
    public boolean isDeny(User user) {
        return user.getRole() != UserRole.ADMIN;
    }
}
