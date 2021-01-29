package com.example.demo.services;

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
        order = orderRepository.save(order);
        log.debug("Order for {} has been submitted", username);
        return order;
    }

    public List<UserOrder> getOrdersForUser(String username) {
        User user = userService.getUserByName(username);
        return orderRepository.findByUser(user);
    }
}
