package com.LieferPlatzProject.LieferPlatz.Service.Implementations;

import com.LieferPlatzProject.LieferPlatz.Model.*;
import com.LieferPlatzProject.LieferPlatz.Repository.OrderRepository;
import com.LieferPlatzProject.LieferPlatz.Repository.OrderItemRepository;
import com.LieferPlatzProject.LieferPlatz.Service.CustomerService;
import com.LieferPlatzProject.LieferPlatz.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerService customerService;



    @Override
    @Transactional
    public Order createOrderFromCart(Long customerId, Cart cart, Long restaurantId) {
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setRestaurantId(restaurantId);

        System.out.println("Order CustomerId: " + order.getCustomerId());
        System.out.println("Order RestaurantId: " + order.getRestaurantId());
        order.setOrderDateTime(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalPrice(cart.getTotalPrice());

        cart.getItems().forEach(ci -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemId(ci.getItemId());
            orderItem.setName(ci.getName());
            orderItem.setQuantity(ci.getQuantity());
            orderItem.setPrice(ci.getPrice());
            order.addOrderItem(orderItem);
        });

        //insertion into table where id or ??? =
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public boolean updateOrderStatus(Long orderId, OrderStatus status, Long restaurantId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getRestaurantId().equals(restaurantId)) {
            return false;
        }

        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
            return false;
        }

        order.setStatus(status);

        //update order where id = orderid
        orderRepository.save(order);
        return true;
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getOrdersByRestaurantId(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }


    // Find active orders for a customer (not DELIVERED or CANCELLED)
    @Override
    public List<Order> findActiveOrdersByCustomerId(Long customerId) {
        List<OrderStatus> inactiveStatuses = Arrays.asList(OrderStatus.DELIVERED, OrderStatus.CANCELLED);
        return orderRepository.findByCustomerIdAndStatusNotIn(customerId, inactiveStatuses);
    }

    // Find all orders for a customer, ordered by order date/time descending
    @Override
    public List<Order> findOrderHistoryByCustomerId(Long customerId) {
        return orderRepository.findByCustomerIdOrderByOrderDateTimeDesc(customerId);
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId, Long customerId) {
        Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));

            // Verify that the order belongs to the customer making the request
        if (!order.getCustomerId().equals(customerId)) {
            throw new SecurityException("Attempt to cancel an order that does not belong to the customer");
        }

            // Ensure the order is in a state that can be canceled
        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order cannot be cancelled as it is in the status " + order.getStatus());
        }

            // Proceed to set the order's status to CANCELLED and save
        order.setStatus(OrderStatus.CANCELLED);

        //update order where id = orderid
        orderRepository.save(order);
    }

    @Override
    public boolean orderBelongsToRestaurant(Long orderId, Long restaurantId) {
        return orderRepository.findById(orderId)
                .map(order -> order.getRestaurantId().equals(restaurantId))
                .orElse(false);
    }




}



