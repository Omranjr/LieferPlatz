package com.LieferPlatzProject.LieferPlatz.Service;

import com.LieferPlatzProject.LieferPlatz.Model.Item;

import java.util.List;

// ItemService.java
public interface ItemService {
    List<Item> getAllItems();
    Item getItemById(Long id);
    Item saveItem(Item item);
    void deleteItem(Long id);
     Item findById(Long id);
    List<Item> getItemsByRestaurant(Long restaurantId);

}
