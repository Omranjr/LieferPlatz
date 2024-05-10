package com.LieferPlatzProject.LieferPlatz.Controller;

import com.LieferPlatzProject.LieferPlatz.Model.Item;
import com.LieferPlatzProject.LieferPlatz.Model.OrderStatus;
import com.LieferPlatzProject.LieferPlatz.Model.Restaurant;
import com.LieferPlatzProject.LieferPlatz.Service.ItemService;
import com.LieferPlatzProject.LieferPlatz.Service.OrderService;
import com.LieferPlatzProject.LieferPlatz.Service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @GetMapping
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }



    @GetMapping("/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }

    @PostMapping("/signup")
    public String createRestaurant(@ModelAttribute Restaurant restaurant,
                                   @RequestParam String openingHours,
                                   @RequestParam String closingHours,
                                   @RequestParam String buildingNumber,
                                   @RequestParam MultipartFile multipartImage, HttpSession session) throws IOException {
        try {

            restaurant.setImage(multipartImage.getBytes());
            restaurantService.createRestaurant(restaurant, openingHours, closingHours, buildingNumber);
            // Redirect to the restaurant dashboard with the restaurant's ID
            session.setAttribute("restaurantId", restaurant.getId());
            return "redirect:/restaurants/dashboard/" + restaurant.getId();
        } catch (Exception e) {
            // Handle signup failure (e.g., display an error message)
            return "redirect:/restaurant/signup"; // Redirect back to the signup page
        }
    }

    @PutMapping("/{id}")
    public void updateRestaurant(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
        restaurantService.updateRestaurant(id, updatedRestaurant);
    }

    @DeleteMapping("/{id}")
    public void deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
    }

    @GetMapping("/dashboard/{restaurantId}")
    public ModelAndView restaurantDashboard(@PathVariable Long restaurantId, Model model, HttpSession session) throws UnsupportedEncodingException {
        if(session.getAttribute("restaurantId") == restaurantId){
            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            if (restaurant != null) {
                model.addAttribute("restaurant", restaurant);
                model.addAttribute("items", itemService.getItemsByRestaurant(restaurantId));
                model.addAttribute("item", new Item());
                // Add orders to the model
                model.addAttribute("orders", orderService.getOrdersByRestaurantId(restaurantId));
                model.addAttribute("image", new ByteArrayResource(restaurant.getImage()));

                // Assuming this method exists

                byte[] encodeBase64 = Base64.getEncoder().encode(restaurant.getImage());
                String base64Encoded = new String(encodeBase64, "UTF-8");
                model.addAttribute("contentImage", base64Encoded);

                ModelAndView modelAndView = new ModelAndView("restaurant-dashboard");
                modelAndView.addObject("contentImage", base64Encoded);
                return modelAndView;
            }
            return new ModelAndView("home");
        }

        return new ModelAndView("access-denied");

    }


    @PostMapping("/login")
    public String restaurantLogin(@RequestParam String restaurantEmail,
                                  @RequestParam String restaurantPassword,
                                  Model model, HttpSession session) {
        Restaurant authenticatedRestaurant = restaurantService.authenticateRestaurant(restaurantEmail, restaurantPassword);

        if (authenticatedRestaurant != null) {
            session.setAttribute("restaurantId", authenticatedRestaurant.getId());
            return "redirect:/restaurants/dashboard/" + authenticatedRestaurant.getId();
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "home";
        }
    }

    @GetMapping("/{restaurantId}/details")
    public String viewRestaurant(@PathVariable Long restaurantId, Model model, HttpSession session) {
            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            List<Item> menuItems = itemService.getItemsByRestaurant(restaurantId);
            session.setAttribute("restaurantId", restaurantId);
            model.addAttribute("restaurant", restaurant);
            model.addAttribute("menuItems", menuItems);
            return "customer-food-view";
    }

    @PostMapping("/orders/{orderId}/updateStatus")
    public String updateOrderStatus(@PathVariable Long orderId,
                                    @RequestParam("status") OrderStatus status,
                                    HttpSession session, RedirectAttributes redirectAttributes) {
        Long sessionRestaurantId = (Long) session.getAttribute("restaurantId");

        // No need to convert from String to Enum, as it's now done automatically
        // Ensure the order belongs to the logged-in restaurant
        if (!orderService.orderBelongsToRestaurant(orderId, sessionRestaurantId)) {
            redirectAttributes.addFlashAttribute("error", "Unauthorized to update the status of this order.");
            return "redirect:/restaurants/dashboard/" + sessionRestaurantId;
        }

        // Update the order status
        boolean updated = orderService.updateOrderStatus(orderId, status, sessionRestaurantId);
        if (!updated) {
            redirectAttributes.addFlashAttribute("error", "Failed to update order status.");
        }

        return "redirect:/restaurants/dashboard/" + sessionRestaurantId;
    }










}

