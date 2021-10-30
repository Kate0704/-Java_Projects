package persistence.storage;

import domain.order.Order;

import java.util.Optional;

public interface OrderStorage {
    public String persist(Order order);

    Optional<Order> load(String id);
}
