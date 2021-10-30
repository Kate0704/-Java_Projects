package persistence.storage;

import domain.order.Order;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class OrderStorageImpl implements OrderStorage {

    @Override
    public String persist(Order order) {
        log.debug("persist({})", order);
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Override
    public Optional<Order> load(String orderId) {
        log.debug("load(orderId={})", orderId);
        throw new UnsupportedOperationException("not implemented yet");
    }
}
