package com.LieferPlatzProject.LieferPlatz.Service.Implementations;

import com.LieferPlatzProject.LieferPlatz.Model.Item;
import com.LieferPlatzProject.LieferPlatz.Repository.ItemRepository;

import com.LieferPlatzProject.LieferPlatz.Service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// ItemServiceImpl.java
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public List<Item> getItemsByRestaurant(Long restaurantId) {
        return itemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public Item findById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

}
