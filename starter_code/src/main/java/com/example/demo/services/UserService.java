package com.example.demo.services;

import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.exceptions.RepositoryException;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            log.debug("Creating new cart for user {}", user.getUsername());
            Cart cart = cartRepository.save(new Cart());
            user.setCart(cart);

            log.debug("Saving user");
            user = userRepository.save(user);
            log.debug("User {} has been saved.", user.getUsername());

            return user;
        } catch (DataIntegrityViolationException exception) {
            throw new RepositoryException(String.format("User \"%s\" could not be saved because user already exists",
                    user.getUsername()));
        }
    }

    public User getUserByName(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException(String.format("Cannot find user \"%s\".", username));
        }
        return user;
    }

    public User getUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent())
            return optionalUser.get();
        else {
            throw new EntityNotFoundException("No user with matching id has been found");
        }
    }
}
