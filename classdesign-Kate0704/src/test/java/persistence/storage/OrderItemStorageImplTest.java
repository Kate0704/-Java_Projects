package persistence.storage;

import domain.order.OrderItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static data.OrderItemTestSamples.anyOrderItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderItemStorageImplTest {
    private OrderItemStorage orderItemStorage;

    @BeforeEach
    void before(){
        orderItemStorage = new OrderItemStorageImpl();
    }

    @Test
    void persistLoad() {
        OrderItem orderItem = anyOrderItem();
        final String id = orderItemStorage.persist(orderItem);
        assertThat(id, is(not(null)));
        assertThat(orderItemStorage.load(id), is(not(null)));
    }

    @Test
    void persist_null() {
        assertThrows(NullPointerException.class, () -> orderItemStorage.persist(null));
    }

    @Test
    void load_null() {
        assertThrows(NullPointerException.class, () -> orderItemStorage.load(null));
    }
}