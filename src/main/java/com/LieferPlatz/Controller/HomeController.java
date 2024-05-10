package com.LieferPlatz.Controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/customer/signup")
    public String customerSignup() {
        return "signup-customer";
    }

    @GetMapping("/restaurant/signup")
    public String restaurantSignup() {
        return "signup-restaurant";
    }
}
