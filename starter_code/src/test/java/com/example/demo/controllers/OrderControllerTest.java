package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private OrderController orderController;
    private final OrderService orderService = mock(OrderService.class);

    @BeforeEach
    private void init() {
        orderController = new OrderController(orderService);
    }

    @Test
    public void verify_submit_happyPath() {
        UserOrder order = UserOrder.createFromCart(TestUtils.createFilledCart(4));
        when(orderService.submitOrderForUser(anyString())).thenReturn(order);

        ResponseEntity<UserOrder> response = orderController.submit("test");

        Assertions.assertEquals(200, response.getStatusCodeValue());

        UserOrder actualOrder = response.getBody();
        Assertions.assertNotNull(actualOrder);
        Assertions.assertEquals(order, actualOrder);
    }

    @Test
    public void verify_getOrdersForUser_happyPath() {
        UserOrder order = UserOrder.createFromCart(TestUtils.createFilledCart(3));
        List<UserOrder> orderList = new ArrayList<>();
        orderList.add(order);

        when(orderService.getOrdersForUser(anyString())).thenReturn(orderList);

        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("test");

        Assertions.assertEquals(200, response.getStatusCodeValue());

        List<UserOrder> actualOrderList = response.getBody();
        Assertions.assertNotNull(actualOrderList);
        Assertions.assertEquals(1, actualOrderList.size());
        Assertions.assertEquals(orderList, actualOrderList);
    }
}
