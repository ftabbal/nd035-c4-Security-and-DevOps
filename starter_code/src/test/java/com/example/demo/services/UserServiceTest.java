package com.example.demo.services;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import static com.example.demo.TestUtils.createUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;
    private final UserRepository repo = mock(UserRepository.class);
    private final CartRepository cartRepo = mock(CartRepository.class);

    @BeforeEach
    public void init() {
        userService = new UserService(repo, cartRepo);
    }

    @Test
    public void verify_createUser_success() {
        when(cartRepo.save(any(Cart.class))).thenReturn(new Cart());

        User expectedUser = createUser();
        when(repo.save(any(User.class))).thenReturn(expectedUser);

        User result = userService.createUser(expectedUser);
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(result.getCart());
    }

    @Test
    public void verify_getUserByName_userExists() {
        User user = createUser();
        when(repo.findByUsername(any(String.class))).thenReturn(user);

        User result = userService.getUserByName("test");
        Assertions.assertNotNull(result);
    }

    @Test
    public void verify_getUserByName_userDoesNotExist() {
        when(repo.findByUsername(any(String.class))).thenReturn(null);

        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> userService.getUserByName("test"));
    }

    @Test
    public void verify_getUserById_userExists() {
        User user = createUser();
        when(repo.findById(any(Long.class))).thenReturn(java.util.Optional.of(user));

        User result = userService.getUserById(0);
        Assertions.assertNotNull(result);
    }

    @Test
    public void verify_getUserById_userDoesNotExist() {
        when(repo.findById(any(Long.class))).thenReturn(java.util.Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class,
                () -> userService.getUserById(0));
    }

}
