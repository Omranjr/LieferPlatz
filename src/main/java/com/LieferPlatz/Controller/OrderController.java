package com.LieferPlatz.Controller;

import com.LieferPlatzProject.LieferPlatz.Model.Cart;
import com.LieferPlatzProject.LieferPlatz.Model.Order;
import com.LieferPlatzProject.LieferPlatz.Model.OrderStatus;
import com.LieferPlatzProject.LieferPlatz.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/checkout")
    public String checkoutOrder(HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");
        Long restaurantId = (Long) session.getAttribute("restaurantId");
        // Ensure you handle customer identification
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
            model.addAttribute("error", "Your cart is empty.");
            return "redirect:/cart/view"; // Redirect back to cart view if empty
        }
        Order order = orderService.createOrderFromCart(customerId, cart, restaurantId);
        session.setAttribute("cart", new Cart()); // Clear the cart after successful checkout
        return "redirect:/order/confirmation/" + order.getId();
    }

    @GetMapping("/order/confirmation/{orderId}")
    public String orderConfirmation(@PathVariable Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        // assuming 'order' includes all needed information
        // ... add any additional attributes to the model as needed
        return "order-confirmation";
    }




}
