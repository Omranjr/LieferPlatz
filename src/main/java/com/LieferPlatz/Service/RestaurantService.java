package com.LieferPlatzProject.LieferPlatz.Service;

import com.LieferPlatzProject.LieferPlatz.Model.Restaurant;
import org.springframework.core.io.Resource;

import java.util.List;


public interface RestaurantService {
    List<Restaurant> getAllRestaurants();
    Restaurant getRestaurantById(Long id);

    void createRestaurant(Restaurant restaurant, String openingHours, String closingHours, String buildingNumber);
    void updateRestaurant(Long id, Restaurant updatedRestaurant);
    void deleteRestaurant(Long id);
    Restaurant authenticateRestaurant(String email, String password);

    List<Restaurant> getAllOpenRestaurants();
}
