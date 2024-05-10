package com.LieferPlatzProject.LieferPlatz.Repository;

import com.LieferPlatzProject.LieferPlatz.Model.Order;
import com.LieferPlatzProject.LieferPlatz.Model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByRestaurantId(Long restaurantId);
    List<Order> findByCustomerIdAndStatusNotIn(Long customerId, List<OrderStatus> status);
    List<Order> findByCustomerIdOrderByOrderDateTimeDesc(Long customerId);
}
