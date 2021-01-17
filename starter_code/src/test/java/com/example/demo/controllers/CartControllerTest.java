package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private CartController cartController;
    private final CartService cartService = mock(CartService.class);

    @BeforeEach
    private void init() {
        this.cartController = new CartController(cartService);
    }

    @Test
    public void verify_addToCart_requestIsValid() {
        ModifyCartRequest request = makeRequest(3);
        Cart cart = TestUtils.createFilledCart(3);
        when(cartService.addToCart(anyString(), anyLong(), anyInt())).thenReturn(cart);

        ResponseEntity<Cart> result = cartController.addToCart(request);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCodeValue());

        Cart actualCart = result.getBody();
        Assertions.assertNotNull(actualCart);
        Assertions.assertEquals(cart, actualCart);
    }

    @Test
    public void verify_removeFromCart_requestIsValid() {
        ModifyCartRequest request = makeRequest(2);
        Cart cart = TestUtils.createFilledCart(2);
        when(cartService.removeFromCart(anyString(), anyLong(), anyInt())).thenReturn(cart);

        ResponseEntity<Cart> response = cartController.removeFromCart(request);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(200, response.getStatusCodeValue());

        Cart actualCart = response.getBody();
        Assertions.assertNotNull(actualCart);
        Assertions.assertEquals(cart, actualCart);
    }

    private ModifyCartRequest makeRequest(int quantity) {
        ModifyCartRequest request = new ModifyCartRequest();
        request.setUsername("test");
        request.setItemId(22);
        request.setQuantity(quantity);
        return request;
    }

}
