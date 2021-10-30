package data;

import domain.order.Order;
import domain.order.OrderItem;

import java.time.LocalDate;
import java.util.List;

import static data.OrderItemTestSamples.*;

public class OrderTestSamples {
    public static Order anyOrder() {
        return anyValidOrder();
    }

    public static Order anyValidOrder() {
        return new Order("1", List.of(anyOrderItem()), LocalDate.now());
    }

    public static Order orderWithNotEmptyItems() {
        List<OrderItem> items = List.of(orderItem1(), orderItem2(), orderItem3());
        return new Order("1", items, LocalDate.now());
    }

}
