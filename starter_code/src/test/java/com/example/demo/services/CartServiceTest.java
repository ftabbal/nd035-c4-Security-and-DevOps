package com.example.demo.services;

import com.example.demo.TestUtils;
import com.example.demo.exceptions.CartException;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartServiceTest {

    private CartService cartService;
    private final CartRepository cartRepository = mock(CartRepository.class);
    private final UserService userService = mock(UserService.class);
    private final ItemService itemService = mock(ItemService.class);

    @BeforeEach
    private void init() {
        this.cartService = new CartService(cartRepository, userService, itemService);
    }

    @Test
    public void verify_addToCart_cartHappyPath() {
        User user = TestUtils.createUser();
        when(userService.getUserByName(anyString())).thenReturn(user);

        Item item = TestUtils.createItem();
        when(itemService.getById(anyLong())).thenReturn(item);

        int numItems = 4;
        Cart expectedCart = TestUtils.createFilledCart(numItems);
        BigDecimal expectedTotal = item.getPrice().multiply(BigDecimal.valueOf(4));

        // Return the user's cart
        when(cartRepository.save(any(Cart.class))).thenReturn(user.getCart());

        Cart cart = cartService.addToCart("test", 22, numItems);

        Assertions.assertNotNull(cart);
        Assertions.assertEquals(expectedTotal, cart.getTotal());
        Assertions.assertEquals(expectedCart, cart);
    }

    @Test
    public void verify_removeFromCart_happyPath() {
        User user = TestUtils.createUser();
        when(userService.getUserByName(anyString())).thenReturn(user);

        Item item = TestUtils.createItem();
        when(itemService.getById(anyLong())).thenReturn(item);


        // Return the user's cart
        when(cartRepository.save(any(Cart.class))).thenReturn(user.getCart());
        Cart cart = cartService.addToCart("test", 22, 4);
        cart = cartService.removeFromCart("test", 22, 2);

        Cart expectedCart = TestUtils.createFilledCart(2);
        BigDecimal expectedTotal = item.getPrice().multiply(BigDecimal.valueOf(2));


        Assertions.assertNotNull(cart);
        Assertions.assertEquals(expectedTotal, cart.getTotal());
        Assertions.assertEquals(expectedCart, cart);
    }

    @Test
    public void verify_addToCart_userDoesNotExist() {
        User user = TestUtils.createUser();
        when(userService.getUserByName(anyString())).thenThrow(EntityNotFoundException.class);

        Item item = TestUtils.createItem();
        when(itemService.getById(anyLong())).thenReturn(item);

        Assertions.assertThrows(CartException.class, () -> {
            cartService.addToCart("test", 22, 2);
        });
    }
}
