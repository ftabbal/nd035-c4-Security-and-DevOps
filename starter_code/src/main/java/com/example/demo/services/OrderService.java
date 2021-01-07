package com.example.demo.services;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;
    private UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public UserOrder submitOrderForUser(String username) {
        User user = userService.getUserByName(username);
        UserOrder order = UserOrder.createFromCart(user.getCart());
        order = orderRepository.save(order);
        return order;
    }

    public List<UserOrder> getOrdersForUser(String username) {
        User user = userService.getUserByName(username);
        return orderRepository.findByUser(user);
    }
}
