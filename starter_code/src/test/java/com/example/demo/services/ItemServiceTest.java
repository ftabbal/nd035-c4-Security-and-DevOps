package com.example.demo.services;

import com.example.demo.TestUtils;
import com.example.demo.exceptions.EntityNotFoundException;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemServiceTest {

    private ItemService itemService;
    private final ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeEach
    private void init() {
        this.itemService = new ItemService(itemRepository);
    }

    @Test
    public void verify_getByName_itemExists() {
        List<Item> itemList = new ArrayList<>();
        itemList.add(TestUtils.createItem());
        itemList.add(TestUtils.createItem());
        when(itemRepository.findByName(anyString())).thenReturn(itemList);

        List<Item> actual = itemService.getByName("test");
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(itemList, actual);
    }

    @Test
    public void verify_getByName_itemDoesNotExists() {
        when(itemRepository.findByName(anyString())).thenReturn(null);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> itemService.getByName("test"));
    }

    @Test
    public void verify_getById_itemExists() {
        Item expected = TestUtils.createItem();
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        Item actual = itemService.getById(0);
        Assertions.assertNotNull(actual);
    }

    @Test
    public void verify_getById_itemDoesNotExists() {
        when(itemRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> itemService.getById(0));
    }

}
