package persistence.storage;

import domain.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static data.OrderTestSamples.anyValidOrder;

class OrderStorageImplTest {
    private OrderStorage orderStorage;

    @BeforeEach
    void before(){
        orderStorage = new OrderStorageImpl();
    }

    @Test
    void persistLoad() {
        Order order = anyValidOrder();
        final String id = orderStorage.persist(order);
        assertThat(id, is(not(null)));
        assertThat(orderStorage.load(id), is(not(null)));
    }

    @Test
    void persist_null() {
        assertThrows(NullPointerException.class, () -> orderStorage.persist(null));
    }

    @Test
    void load_null() {
        assertThrows(NoSuchElementException.class, () -> orderStorage.load(null));
    }
}