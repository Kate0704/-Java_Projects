package validation;

import domain.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import static domain.order.OrderStatus.INITIAL;

@Slf4j
public class OrderValidator {

    public boolean isValidOrder(@NotNull Order order) {
        log.debug("isValidOrder({})", order);
        return (order.getUserId() != null) && (order.getItems() != null) && (order.getStatus() == INITIAL);
    }
}

