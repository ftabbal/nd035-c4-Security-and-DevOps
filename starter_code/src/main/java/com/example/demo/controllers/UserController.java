package com.example.demo.controllers;

import com.example.demo.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.CreateUserRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id) {
		User user = userService.getUserById(id);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<User> findByUserName(@PathVariable String username) {
		User user = userService.getUserByName(username);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
		if ( createUserRequest.getPassword().length() < 7
				|| !createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
			return ResponseEntity.badRequest().build();
		}
		User user = new User();
		user.setUsername(createUserRequest.getUsername());
		user.setPassword(bCryptPasswordEncoder.encode(createUserRequest.getPassword()));
		user = userService.createUser(user);
		return ResponseEntity.ok(user);
	}
	
}
