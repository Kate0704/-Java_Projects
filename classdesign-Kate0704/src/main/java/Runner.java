import domain.order.Order;
import domain.order.OrderItem;
import domain.user.User;
import domain.user.info.Address;
import persistence.service.OrderService;
import persistence.service.OrderServiceImpl;
import persistence.service.UserService;
import persistence.service.UserServiceImpl;
import persistence.storage.OrderStorageImpl;
import persistence.storage.UserStorageImpl;
import validation.OrderValidator;
import validation.UserValidator;

import java.time.LocalDate;
import java.util.List;

public class Runner {
    public static void main(String[] args) {
        String id = "1";
        User user = new User("firstUser", "Katerina", "Zhevniak");
        user.setAddress(new Address("Gomel region", "Rechitsa", "Sovetskaya", "97A", 21));
        OrderItem orderItem = new OrderItem("orange", 3, 30);
        Order order = new Order(id, List.of(orderItem), LocalDate.now());
        UserService userService = new UserServiceImpl(new UserStorageImpl(), new UserValidator());
        OrderService orderService = new OrderServiceImpl(new OrderStorageImpl(), userService, new OrderValidator());
        userService.createUser(user);
        //userService.findById(id);

        userService.findById(id);
        orderService.placeOrder(order);
        orderService.loadAllByUserId(id);

    }
}
