package domain.user;

import domain.order.Order;
import domain.user.info.Address;
import domain.user.info.Age;
import domain.user.info.UserRole;
import domain.user.info.UserStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class User {
    private String id;
    private String username;
    private String name;
    private String surname;
    private Address address;
    private UserRole role;
    private UserStatus status;
    private Age age;
    private List<Order> orders;

    private User() {
        this.role = UserRole.USER;
        this.status = UserStatus.INITIAL;
        this.orders = new ArrayList<>();
    }

    public User(String username) {
        this();
        this.username = username;
    }

    public User(String username, String name, String surname) {
        this(username);
        this.name = name;
        this.surname = surname;
    }
}
