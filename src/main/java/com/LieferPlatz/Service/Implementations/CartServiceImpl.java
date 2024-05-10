package com.LieferPlatzProject.LieferPlatz.Service.Implementations;

import com.LieferPlatzProject.LieferPlatz.Model.Cart;
import com.LieferPlatzProject.LieferPlatz.Model.CartItem;
import com.LieferPlatzProject.LieferPlatz.Model.Item;
import com.LieferPlatzProject.LieferPlatz.Service.CartService;
import com.LieferPlatzProject.LieferPlatz.Service.ItemService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ItemService itemService; // Assume this service can fetch item details by ID

    @Override
    public void addItemToCart(Long itemId, int quantity, HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }
        Item item = itemService.findById(itemId);
        CartItem cartItem = new CartItem();
        cartItem.setItemId(item.getId());
        cartItem.setName(item.getName());
        cartItem.setQuantity(quantity);
        cartItem.setPrice(item.getPrice());

        cart.addItem(cartItem);
        session.setAttribute("cart", cart);
    }

    @Override
    public Cart getCart(HttpSession session) {
        return (Cart) session.getAttribute("cart");
    }

    @Override
    public void removeItemFromCart(Long itemId, HttpSession session) {
        Cart cart = getCart(session);
        cart.removeItem(itemId);
        session.setAttribute("cart", cart);
    }

    @Override
    public void updateItemQuantity(Long itemId, int quantity, HttpSession session) {
        Cart cart = getCart(session);
        cart.updateItemQuantity(itemId, quantity);
        session.setAttribute("cart", cart);
    }
}


