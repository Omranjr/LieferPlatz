package com.LieferPlatzProject.LieferPlatz.Repository;


import com.LieferPlatzProject.LieferPlatz.Model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByRestaurantId(Long restaurantId);

}
