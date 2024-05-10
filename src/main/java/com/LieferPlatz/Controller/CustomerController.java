package com.LieferPlatz.Controller;

import com.LieferPlatzProject.LieferPlatz.Model.Customer;
import com.LieferPlatzProject.LieferPlatz.Model.Item;
import com.LieferPlatzProject.LieferPlatz.Model.Order;
import com.LieferPlatzProject.LieferPlatz.Model.Restaurant;
import com.LieferPlatzProject.LieferPlatz.Service.CustomerService;
import com.LieferPlatzProject.LieferPlatz.Service.ItemService;
import com.LieferPlatzProject.LieferPlatz.Service.OrderService;
import com.LieferPlatzProject.LieferPlatz.Service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping("/signup")
    public String createCustomer(@ModelAttribute Customer customer,
                                 @RequestParam String buildingNumber) {
        try {
            Customer createdCustomer = customerService.createCustomer(customer, buildingNumber);
            // Redirect to the customer dashboard with the customer's ID
            return "redirect:/customers/dashboard/" + createdCustomer.getId();
        } catch (RuntimeException e) {
            // Handle signup failure (e.g., display an error message)
            return "redirect:/customer/signup"; // Redirect back to the signup page
        }
    }

    @PutMapping("/{id}")
    public void updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        customerService.updateCustomer(id, updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }


    @GetMapping("/dashboard/{customerId}")
    public String customerDashboard(@PathVariable Long customerId, Model model, HttpSession session) {
        if(session.getAttribute("customerId") == customerId){
            Customer customer = customerService.getCustomerById(customerId);
            List<Order> activeOrders = orderService.findActiveOrdersByCustomerId(customerId);
            List<Restaurant> openRestaurants = restaurantService.getAllOpenRestaurants();

            List<Restaurant> inRadiusRestaurant = new ArrayList<Restaurant>();
            for (var restaurant : openRestaurants) {
                if(restaurant.getRadius() != null && restaurant.getRadius().contains(customer.getZipCode())){
                    inRadiusRestaurant.add(restaurant);
                }
            }

            model.addAttribute("customer", customer);
            model.addAttribute("activeOrders", activeOrders);
            model.addAttribute("openRestaurants", inRadiusRestaurant);


            return "customer-dashboard";
        }

        return "access-denied";
    }


    @PostMapping("/login")
    public String customerLogin(@RequestParam String customerEmail, @RequestParam String customerPassword, Model model, HttpSession session) {

        //select * from customer where password = pass and email = email
        Customer authenticatedCustomer = customerService.authenticateCustomer(customerEmail, customerPassword);

        if (authenticatedCustomer != null) {
            session.setAttribute("customerId", authenticatedCustomer.getId());
            // Redirect to the customer dashboard with the customer's ID
            return "redirect:/customers/dashboard/" + authenticatedCustomer.getId();
        } else {
            // Add an error message to the model and stay on the same page
            model.addAttribute("error", "Invalid credentials");
            return "home";
        }
    }

    @GetMapping("/dashboard/{customerId}/order-history")
    public String viewOrderHistory(@PathVariable Long customerId, Model model, HttpSession session) {
        if(session.getAttribute("customerId") == customerId){
            // In your controller method, ensure the list is not null
            List<Order> orderHistory = orderService.findOrderHistoryByCustomerId(customerId);
            if (orderHistory == null) {
                orderHistory = new ArrayList<>(); // Ensure it's not null
            }
            model.addAttribute("orderHistory", orderHistory);

            return "order-history";
        }
        return "access-denied";
    }


    // Inside CustomerController
    @PostMapping("/order/cancel")
    public String cancelOrder(@RequestParam Long orderId, HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) {
            return "redirect:/login";
        }

        orderService.cancelOrder(orderId, customerId);

        return "redirect:/customers/dashboard/" + customerId;
    }









}
