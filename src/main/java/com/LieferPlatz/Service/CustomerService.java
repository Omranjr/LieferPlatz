package com.LieferPlatzProject.LieferPlatz.Service;


import com.LieferPlatzProject.LieferPlatz.Model.Customer;
import com.LieferPlatzProject.LieferPlatz.Model.Order;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Long id);
    Customer createCustomer(Customer customer, String buildingNumber);
    void updateCustomer(Long id, Customer updatedCustomer);
    void deleteCustomer(Long id);
    Customer authenticateCustomer(String email, String password);

}
