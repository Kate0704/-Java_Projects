package domain.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static data.OrderTestSamples.orderWithNotEmptyItems;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class OrderTest {
    private Order order;

    @BeforeEach
    void before() {
        order = orderWithNotEmptyItems();
    }

    @Test
    void getOrderPrice() {
        assertThat(order.getOrderPrice(), equalTo(1000L));
    }
}