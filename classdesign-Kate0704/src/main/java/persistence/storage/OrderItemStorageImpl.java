package persistence.storage;

import domain.order.OrderItem;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderItemStorageImpl implements OrderItemStorage {

    @Override
    public String persist(OrderItem orderItem) {
        log.debug("persist(orderItem={})", orderItem);
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public OrderItem load(String orderItemID) {
        log.debug("load(orderItemID={})", orderItemID);
        throw new UnsupportedOperationException("not implemented yet");
    }
}
