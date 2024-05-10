package com.LieferPlatzProject.LieferPlatz.Service;

import com.LieferPlatzProject.LieferPlatz.Model.Cart;
import com.LieferPlatzProject.LieferPlatz.Model.Order;
import com.LieferPlatzProject.LieferPlatz.Model.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrderFromCart(Long customerId, Cart cart, Long restaurantId);
    boolean updateOrderStatus(Long orderId, OrderStatus status, Long restaurantId);
    Order findById(Long orderId);
    List<Order> getOrdersByRestaurantId(Long restaurantId);

    public List<Order> findActiveOrdersByCustomerId(Long customerId);
    public List<Order> findOrderHistoryByCustomerId(Long customerId);

    void cancelOrder(Long orderId, Long customerId);

    boolean orderBelongsToRestaurant(Long orderId, Long restaurantId);



}
