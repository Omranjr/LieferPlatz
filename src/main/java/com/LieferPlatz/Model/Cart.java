package com.LieferPlatzProject.LieferPlatz.Model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addItem(CartItem item) {
        CartItem existingItem = findItemById(item.getItemId());
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
        } else {
            items.add(item);
        }
    }

    private CartItem findItemById(Long itemId) {
        return items.stream().filter(item -> item.getItemId().equals(itemId)).findFirst().orElse(null);
    }

    public double getTotalPrice() {
        return items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    public void removeItem(Long itemId) {
        items.removeIf(item -> item.getItemId().equals(itemId));
    }

    public void updateItemQuantity(Long itemId, int quantity) {
        CartItem item = findItemById(itemId);
        if (item != null) {
            item.setQuantity(quantity);
        }
    }

    // Getters and Setters
    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Cart() {}

}
