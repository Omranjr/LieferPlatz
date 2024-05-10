package com.LieferPlatz.Controller;

import com.LieferPlatzProject.LieferPlatz.Model.Item;
import com.LieferPlatzProject.LieferPlatz.Model.Restaurant;
import com.LieferPlatzProject.LieferPlatz.Service.ItemService;
import com.LieferPlatzProject.LieferPlatz.Service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

// ItemController.java
@Controller
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public String getAllItems(Model model) {
        List<Item> items = itemService.getAllItems();
        for (Item item : items) {
            // Set the restaurant field for each item
            item.setRestaurant(restaurantService.getRestaurantById(item.getRestaurant().getId()));
        }
        model.addAttribute("items", items);
        model.addAttribute("item", new Item()); // Empty item for the form
        return "restaurant-dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editItemForm(@PathVariable Long id, Model model) {
        Item item = itemService.getItemById(id);
        if (item != null && item.getRestaurant() != null) {
            model.addAttribute("item", item);
            // Also add the restaurant to the model to maintain context
            model.addAttribute("restaurant", item.getRestaurant());
            return "restaurant-dashboard";
        }
        // Handle the case where the item or restaurant does not exist
        return "redirect:/error";

    }

    @PostMapping("/save")
    public String saveItem(@ModelAttribute Item item, @RequestParam("restaurantId") Long restaurantId, Model model, @RequestParam MultipartFile multipartImage) throws IOException {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant == null) {
            return "redirect:/error"; // Or suitable error handling
        }
        item.setRestaurant(restaurant);

        if (item.getId() != null) {
            // Existing item, update it
            Item existingItem = itemService.getItemById(item.getId());
            if (existingItem != null) {
                existingItem.setName(item.getName());
                existingItem.setDescription(item.getDescription());
                existingItem.setPrice(item.getPrice());
                // Update any other fields
                item = existingItem; // Use the updated existing item
            } // Consider adding an else block to handle item not found
        }
        // Save (new or updated existing) item
        if(!multipartImage.isEmpty())
            item.setImage(multipartImage.getBytes());
        itemService.saveItem(item);
        model.addAttribute("restaurant", restaurant);
        return "redirect:/restaurants/dashboard/" + restaurantId;
    }

    // Change the method to handle both GET and POST requests
    @RequestMapping(value = "/delete/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteItem(@PathVariable(required = false) Long id, @RequestParam("restaurantId") Long restaurantId) {
        if (id != null) {
            itemService.deleteItem(id);
        }


        return "redirect:/restaurants/dashboard/" + restaurantId;
    }


}
