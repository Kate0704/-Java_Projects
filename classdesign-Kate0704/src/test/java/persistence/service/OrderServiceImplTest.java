package persistence.service;

import domain.order.Order;
import domain.order.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.storage.OrderStorageImpl;
import persistence.storage.UserStorage;
import persistence.storage.UserStorageImpl;
import validation.OrderValidator;
import validation.UserValidator;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static data.OrderTestSamples.anyOrder;
import static data.UserTestSamples.anyValidUser;

class OrderServiceImplTest {
    private Order order;
    private OrderService orderService;
    private UserService userService;
    private UserStorage userStorage;
    private OrderStorageImpl orderStorage;
    private OrderValidator orderValidator;

    @BeforeEach
    public void before() {
        order = anyOrder();
        userStorage = mock(UserStorageImpl.class);
        UserValidator userValidator = mock(UserValidator.class);
        orderStorage = mock(OrderStorageImpl.class);
        orderValidator = mock(OrderValidator.class);

        when(orderStorage.persist(any())).thenReturn(UUID.randomUUID().toString());
        userService = new UserServiceImpl(userStorage, userValidator);
        orderService = new OrderServiceImpl(orderStorage, userService, orderValidator);
    }

    @Test
    void placeOrder() {
        when(orderValidator.isValidOrder(order)).thenReturn(true);

        String id = orderService.placeOrder(order);

        verify(orderStorage).persist(order);
        assertThat(order.getStatus(), is(OrderStatus.ACCEPTED));
        assertThat(id, notNullValue());
    }

    @Test
    void placeOrder_invalidOrder() {
        when(orderValidator.isValidOrder(order)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> orderService.placeOrder(order));
        verify(orderStorage, never()).persist(order);
        assertThat(order.getStatus(), is(OrderStatus.INITIAL));
    }

    @Test
    void loadAllByUserId() {
        String userId = UUID.randomUUID().toString();
        when(userStorage.load(any())).thenReturn(Optional.of(anyValidUser()));
        orderService.loadAllByUserId(userId);

        verify(userStorage).load(userId);
    }

    @Test
    void loadAllByUserId_invalidUserId() {
        String userId = UUID.randomUUID().toString();
        when(userService.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.loadAllByUserId(userId));
    }
}