package com.example.demo.controllers;


import com.example.demo.services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.requests.ModifyCartRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	private final CartService cartService;

	private static final Logger log = LoggerFactory.getLogger(CartController.class);

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {
		log.debug("Attempting to add item(s) to cart for user {}", request.getUsername());
		Cart cart = cartService.addToCart(request.getUsername(), request.getItemId(), request.getQuantity());
		log.info("Cart has been updated");
		return ResponseEntity.ok(cart);
	}
	
	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
		Cart cart = cartService.removeFromCart(request.getUsername(), request.getItemId(), request.getQuantity());
		log.info("Cart has been updated");
		return ResponseEntity.ok(cart);
	}
}
