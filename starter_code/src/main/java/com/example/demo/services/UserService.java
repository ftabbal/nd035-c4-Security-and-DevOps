package com.example.demo.services;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public UserService(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public User createUser(User user) {
        Cart cart = cartRepository.save(new Cart());
        user.setCart(cart);
        user = userRepository.save(user);
        return user;
    }

    public User getUserByName(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ObjectNotFoundException(String.format("Cannot find user \"%s\".", username));
        }
        return user;
    }

    public User getUserById(long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent())
            return optionalUser.get();
        else
            throw new ObjectNotFoundException("Object not found!");
    }
}
