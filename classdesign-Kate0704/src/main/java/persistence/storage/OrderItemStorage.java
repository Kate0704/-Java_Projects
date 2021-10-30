package persistence.storage;

import domain.order.OrderItem;

public interface OrderItemStorage {
    public String persist(OrderItem orderItem);

    public OrderItem load(String orderItemID);
}
