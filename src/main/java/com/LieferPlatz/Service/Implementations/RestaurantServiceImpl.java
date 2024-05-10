package com.LieferPlatzProject.LieferPlatz.Service.Implementations;


import com.LieferPlatzProject.LieferPlatz.Model.Item;
import com.LieferPlatzProject.LieferPlatz.Model.Restaurant;
import com.LieferPlatzProject.LieferPlatz.Repository.RestaurantRepository;
import com.LieferPlatzProject.LieferPlatz.Service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    @Override
    public void createRestaurant(Restaurant restaurant, String openingHours, String closingHours, String buildingNumber) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        restaurant.setOpeningHours(LocalTime.parse(openingHours, formatter));
        restaurant.setClosingHours(LocalTime.parse(closingHours, formatter));
        restaurant.setBuildingNumber(buildingNumber);
        restaurantRepository.save(restaurant);
    }

    @Override
    public void updateRestaurant(Long id, Restaurant updatedRestaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(id).orElse(null);

        if (existingRestaurant != null) {
            // Update the fields you want to allow modification
            existingRestaurant.setName(updatedRestaurant.getName());
            existingRestaurant.setEmail(updatedRestaurant.getEmail());
            existingRestaurant.setCity(updatedRestaurant.getCity());
            existingRestaurant.setStreet(updatedRestaurant.getStreet());
            existingRestaurant.setBuildingNumber(updatedRestaurant.getBuildingNumber());
            existingRestaurant.setZipCode(updatedRestaurant.getZipCode());
            existingRestaurant.setDescription(updatedRestaurant.getDescription());
            existingRestaurant.setPassword(updatedRestaurant.getPassword());

            // Ensure that the ID is set before saving
            existingRestaurant.setId(id);
            for (Item item : updatedRestaurant.getMenu()) {
                item.setRestaurant(existingRestaurant);
            }

            restaurantRepository.save(existingRestaurant);
        }
    }


    @Override
    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public Restaurant authenticateRestaurant(String email, String password) {
        return restaurantRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public List<Restaurant> getAllOpenRestaurants() {
        LocalTime currentTime = LocalTime.now();
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        List<Restaurant> openRestaurants = new ArrayList<>();

        for (Restaurant restaurant : allRestaurants) {
            if (isRestaurantOpen(restaurant, currentTime)) {
                openRestaurants.add(restaurant);
            }
        }

        return openRestaurants;
    }


    private boolean isRestaurantOpen(Restaurant restaurant, LocalTime currentTime) {
        LocalTime openingTime = restaurant.getOpeningHours();
        LocalTime closingTime = restaurant.getClosingHours();

        // If closing time is before opening time, it means the restaurant closes after midnight.
        if (closingTime.isBefore(openingTime)) {
            // If current time is after opening OR before closing, the restaurant is open.
            if (currentTime.isAfter(openingTime) || currentTime.isBefore(closingTime)) {
                return true;
            }
        } else {
            // For the normal case where the restaurant does not close after midnight
            if (currentTime.isAfter(openingTime) && currentTime.isBefore(closingTime)) {
                return true;
            }
        }
        return false;
    }

}
