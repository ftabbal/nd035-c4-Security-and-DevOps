package com.example.demo.services;

import com.example.demo.controllers.UserController;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public User createUser(User user) {
        log.debug("Creating new cart for user {}", user.getUsername());
        Cart cart = cartRepository.save(new Cart());
        user.setCart(cart);

        log.debug("Saving user");
        user = userRepository.save(user);
        log.info("User {} has been saved.", user.getUsername());

        return user;
    }

    public User getUserByName(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.warn("User {} does not exist!", username);
            throw new ObjectNotFoundException(String.format("Cannot find user \"%s\".", username));
        }
        return user;
    }

    public User getUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent())
            return optionalUser.get();
        else {
            log.warn("Attempted to get a non-existing user for id {}", id);
            throw new ObjectNotFoundException("Object not found!");
        }
    }
}
