package persistence.service;

import domain.order.Order;
import domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import persistence.storage.OrderStorage;
import validation.OrderValidator;

import java.util.List;
import java.util.NoSuchElementException;

import static domain.order.OrderStatus.ACCEPTED;

@Slf4j
@RequiredArgsConstructor
public final class OrderServiceImpl implements OrderService {
    private final OrderStorage orderStorage;
    private final UserService userService;
    private final OrderValidator orderValidator;

    @Override
    public String placeOrder(Order order) {
        log.debug("placeOrder({}, {}, {})",
                orderStorage,
                orderValidator,
                userService);

        final boolean valid = orderValidator.isValidOrder(order);
        if (!valid) {
            log.error("order is invalid");
            throw new IllegalArgumentException("order is not valid" + order);
        }
        log.debug("order is valid");

        final String id = orderStorage.persist(order);
        order.setStatus(ACCEPTED);
        log.debug("set order status to accepted");

        order.setId(id);
        log.debug("set user id to " + id);

        return id;
    }

    @Override
    public List<Order> loadAllByUserId(String userId) {
        log.debug("placeOrder(userId={})", userId);
        User user = userService.findById(userId).orElseThrow(NoSuchElementException::new);
        log.debug("user: " + user);
        return user.getOrders();
    }

}
