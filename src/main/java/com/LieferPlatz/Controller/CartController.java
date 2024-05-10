package com.LieferPlatz.Controller;

import com.LieferPlatzProject.LieferPlatz.Model.Cart;
import com.LieferPlatzProject.LieferPlatz.Model.Item;
import com.LieferPlatzProject.LieferPlatz.Model.Restaurant;
import com.LieferPlatzProject.LieferPlatz.Service.CartService;
import com.LieferPlatzProject.LieferPlatz.Service.ItemService;
import com.LieferPlatzProject.LieferPlatz.Service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CartController {

    @Autowired
    private CartService cartService;


    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("itemId") Long itemId,
                            @RequestParam("quantity") int quantity,
                            HttpSession session) {
        cartService.addItemToCart(itemId, quantity, session);
        return "redirect:/cart/view"; // Redirect to the cart view
    }

    @PostMapping("/cart/delete")
    public String deleteFromCart(@RequestParam("itemId") Long itemId,
                                 HttpSession session) {
        cartService.removeItemFromCart(itemId, session);
        return "redirect:/cart/view"; // Redirect to the cart view
    }

    @PostMapping("/cart/update")
    public String updateCartItem(@RequestParam("itemId") Long itemId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session) {
        cartService.updateItemQuantity(itemId, quantity, session);
        return "redirect:/cart/view"; // Redirect to the cart view
    }

    @GetMapping("/cart/view")
    public String viewCart(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        return "cart-view"; // Path to the Thymeleaf template
    }


}

