package com.example.orderservice.service;

import com.example.orderservice.model.Item;
import com.example.orderservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public void saveItems(List<Item> items) {
        itemRepository.saveAll(items);
    }
}
