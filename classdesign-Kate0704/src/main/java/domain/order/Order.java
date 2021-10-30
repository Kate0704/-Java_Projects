package domain.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static domain.order.OrderStatus.INITIAL;

@Getter
@Setter
@ToString
public class Order {
    private String id;
    private final String userId;
    private OrderStatus status;
    private final List<OrderItem> items;
    private final LocalDate date;

    public Order(String userId, List<OrderItem> items, LocalDate date) {
        this.userId = userId;
        this.items = items;
        this.date = date;
        this.status = INITIAL;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public long getOrderPrice() {
        return items.stream()
                .mapToInt(i -> i.getPrice() * i.getQuantity())
                .sum();
    }
}
