package com.LieferPlatzProject.LieferPlatz.Repository;

import com.LieferPlatzProject.LieferPlatz.Model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByEmailAndPassword(String email, String password);

}