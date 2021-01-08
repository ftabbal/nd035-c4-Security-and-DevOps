package com.example.demo.services;

import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item getById(long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            return optionalItem.get();
        } else {
            throw new ObjectNotFoundException("Cannot find item with id: " + id);
        }
    }

    public List<Item> getByName(String itemName) {
        List<Item> itemList = itemRepository.findByName(itemName);
        if (itemList == null || itemList.isEmpty()) {
            throw new ObjectNotFoundException(String.format("Cannot find item \"%s\"", itemName));
        }

        return itemList;
    }
}
