package com.LieferPlatzProject.LieferPlatz.Service;

import com.LieferPlatzProject.LieferPlatz.Model.Cart;
import jakarta.servlet.http.HttpSession;

public interface CartService {
    void addItemToCart(Long itemId, int quantity, HttpSession session);

    void removeItemFromCart(Long itemId, HttpSession session);

    void updateItemQuantity(Long itemId, int quantity, HttpSession session);
    Cart getCart(HttpSession session);
}

