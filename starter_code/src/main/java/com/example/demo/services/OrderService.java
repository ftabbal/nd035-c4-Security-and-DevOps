package com.example.demo.services;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.OrderException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);


    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public UserOrder submitOrderForUser(String username) {
        log.debug("Attempting to submit order for {}", username);
        User user = userService.getUserByName(username);
        UserOrder order = UserOrder.createFromCart(user.getCart());
        if (order.getItems().isEmpty()) {
            log.warn("Attempted to submit an empty order.");
            throw new OrderException("Cannot submit an empty order.");
        }
        order = orderRepository.save(order);
        log.info("Order for {} has been submitted", username);
        user.setCart(new Cart());
        return order;
    }

    public List<UserOrder> getOrdersForUser(String username) {
        try {
            User user = userService.getUserByName(username);
            return orderRepository.findByUser(user);
        } catch (EntityNotFoundException ex) {
            log.warn(String.format("Cannot get orders for user %s. Reason: %s", username, ex.getMessage()));
            throw new OrderException(String.format("Cannot get orders for user %s. Reason: %s",
                    username, ex.getMessage()));
        }
    }
}
