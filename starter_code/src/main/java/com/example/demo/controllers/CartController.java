package com.example.demo.controllers;


import com.example.demo.services.CartService;
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

	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@PostMapping("/addToCart")
	public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {

		Cart cart = cartService.addToCart(request.getUsername(), request.getItemId(), request.getQuantity());
		return ResponseEntity.ok(cart);
	}
	
	@PostMapping("/removeFromCart")
	public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
		Cart cart = cartService.removeFromCart(request.getUsername(), request.getItemId(), request.getQuantity());
		return ResponseEntity.ok(cart);
	}
}
