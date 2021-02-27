package com.example.demo.services;

import com.example.demo.TestUtils;
import com.example.demo.exceptions.OrderException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderServiceTest {

    private OrderService orderService;

    private final UserService userService = mock(UserService.class);
    private final OrderRepository orderRepository = mock(OrderRepository.class);

    @BeforeEach
    private void init() {
        this.orderService = new OrderService(orderRepository, userService);
    }

    @Test
    public void verify_submitOrderForUser_happyPath() {
        // I have some doubts about this test.
        User user = TestUtils.createUser();
        Cart cart = TestUtils.createFilledCart(3);
        user.setCart(cart);
        UserOrder order = UserOrder.createFromCart(cart);

        when(userService.getUserByName(anyString())).thenReturn(user);
        when(orderRepository.save(any(UserOrder.class))).thenReturn(order);

        UserOrder actualOrder = orderService.submitOrderForUser("test");

        Assertions.assertNotNull(actualOrder);
        Assertions.assertEquals(order, actualOrder);
    }

    @Test
    public void verify_submitOrder_cannotSubmitEmptyCart() {
        User user = TestUtils.createUser();
        user.setCart(new Cart());
        when(userService.getUserByName(anyString())).thenReturn(user);

        Assertions.assertThrows(OrderException.class, () -> {
            orderService.submitOrderForUser("test");
        });
    }

    @Test
    public void verify_submitOrder_cannotResubmitSameOrder() {

    }

    @Test
    public void verify_getOrdersForUser_happyPath() {
        // Not sure about this test as well.
        User user = TestUtils.createUser();
        Cart cart = TestUtils.createFilledCart(3);
        List<UserOrder> orderList = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            orderList.add(UserOrder.createFromCart(cart));
        }

        when(userService.getUserByName(anyString())).thenReturn(user);
        when(orderRepository.findByUser(any(User.class))).thenReturn(orderList);

        Assertions.assertNotNull(orderList);
    }

}
