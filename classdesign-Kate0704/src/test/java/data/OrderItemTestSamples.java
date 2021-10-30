package data;

import domain.order.OrderItem;

public class OrderItemTestSamples {
    public static OrderItem anyOrderItem(){
        return orderItem1();
    }

    public static OrderItem orderItem1() {
        return new OrderItem("apple", 8, 10);
    }

    public static OrderItem orderItem2() {
        return new OrderItem("orange", 4, 30);
    }

    public static OrderItem orderItem3() {
        return new OrderItem("avocado", 8, 100);
    }
}
