package persistence.service;

import domain.order.Order;

import java.util.List;

public interface OrderService {
    String placeOrder(Order order);

    public List<Order> loadAllByUserId(String userId);
}
