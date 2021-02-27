package com.example.demo.services;

import com.example.demo.controllers.UserController;
import com.example.demo.exceptions.CartException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ItemService itemService;

    private static final Logger log = LoggerFactory.getLogger(CartService.class);


    public CartService(CartRepository cartRepository, UserService userService, ItemService itemService) {
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.itemService = itemService;
    }

    public Cart addToCart(String username, long itemId, int quantity) {
        try {
            User user = userService.getUserByName(username);
            Item item = itemService.getById(itemId);
            Cart cart = user.getCart();

            IntStream.range(0, quantity)
                    .forEach(i -> cart.addItem(item));

            Cart result =  cartRepository.save(cart);
           log.info("Cart has been saved");
           return result;
        } catch (EntityNotFoundException ex) {
            log.warn(String.format("Cart could not be saved. Reason: %s", ex.getMessage()));
            throw new CartException(String.format("Cart could not be saved. Reason: %s", ex.getMessage()));
        }
    }

    public Cart removeFromCart(String username, long itemId, int quantity) {
        User user = userService.getUserByName(username);
        Item item = itemService.getById(itemId);
        Cart cart = user.getCart();

        IntStream.range(0, quantity)
                .forEach(i -> cart.removeItem(item));

        return cartRepository.save(cart);
    }
}
