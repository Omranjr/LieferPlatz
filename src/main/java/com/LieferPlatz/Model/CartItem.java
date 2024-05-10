package com.LieferPlatzProject.LieferPlatz.Model;

public class CartItem {
    private Long itemId;
    private String name;
    private int quantity;
    private double price;

    // Constructors, Getters, and Setters

    public CartItem() {}

    public Long getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
}