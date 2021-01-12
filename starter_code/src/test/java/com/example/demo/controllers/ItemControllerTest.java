package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.services.ItemService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private ItemController itemController;
    private final ItemService itemService = mock(ItemService.class);

    @BeforeEach
    private void init() {
        this.itemController = new ItemController(itemService);
    }

    @Test
    public void verify_getItems() {
        List<Item> itemList = new ArrayList<>();
        Item expectedItem = TestUtils.createItem();
        itemList.add(expectedItem);
        when(itemService.getAll()).thenReturn(itemList);

        ResponseEntity<List<Item>> result = itemController.getItems();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCodeValue());

        List<Item> resultItems = result.getBody();
        Assertions.assertNotNull(resultItems);
        Assertions.assertEquals(1, itemList.size());
        Assertions.assertEquals(expectedItem, resultItems.get(0));
    }

    @Test
    public void verify_getItemById() {
        Item expected = TestUtils.createItem();
        when(itemService.getById(anyLong())).thenReturn(expected);

        ResponseEntity<Item> result = itemController.getItemById(0L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCodeValue());

        Item resultItem = result.getBody();
        Assertions.assertNotNull(resultItem);
        Assertions.assertEquals(expected, resultItem);
    }

    @Test
    public void verify_getItemsByName() {
        List<Item> itemList = new ArrayList<>();
        Item expectedItem = TestUtils.createItem();
        itemList.add(expectedItem);
        when(itemService.getByName(anyString())).thenReturn(itemList);

        ResponseEntity<List<Item>> result = itemController.getItemsByName("test");

        Assertions.assertNotNull(result);
        Assertions.assertEquals(200, result.getStatusCodeValue());

        List<Item> resultItems = result.getBody();
        Assertions.assertNotNull(resultItems);
        Assertions.assertEquals(1, resultItems.size());
        Assertions.assertEquals(expectedItem, resultItems.get(0));
    }
}
