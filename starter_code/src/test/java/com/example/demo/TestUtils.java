package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;

import java.lang.reflect.Field;
import java.math.BigDecimal;

public class TestUtils {

    public static void injectObject(Object target, String fieldName, Object toInject) {
        boolean wasPrivate = false;
        try {
            Field declaredField = target.getClass().getDeclaredField(fieldName);

            if (!declaredField.canAccess(target)) {
                declaredField.setAccessible(true);
                wasPrivate = true;
            }
            declaredField.set(target, toInject);

            if (wasPrivate) {
                declaredField.setAccessible(false);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static User createUser() {
        User user = new User();
        user.setPassword("thisIsHashed");
        user.setId(0);
        user.setUsername("test");
        user.setCart(new Cart());
        return user;
    }

    public static Item createItem() {
        Item item = new Item();
        item.setId(0L);
        item.setName("testItem");
        item.setDescription("A test item");
        item.setPrice(BigDecimal.valueOf(12L));
        return item;
    }

    public static Cart createFilledCart(int numItems) {
        Cart cart = new Cart();
        Item item = createItem();

        for (int i = 0; i < numItems; ++i) {
            cart.addItem(item);
        }

        return cart;
    }
}
