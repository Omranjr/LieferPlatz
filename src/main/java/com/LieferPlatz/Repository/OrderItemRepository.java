package com.LieferPlatzProject.LieferPlatz.Repository;

import com.LieferPlatzProject.LieferPlatz.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Custom query methods if needed
}


