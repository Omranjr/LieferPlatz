package com.LieferPlatzProject.LieferPlatz.Service.Implementations;


import com.LieferPlatzProject.LieferPlatz.Model.Customer;
import com.LieferPlatzProject.LieferPlatz.Repository.CustomerRepository;
import com.LieferPlatzProject.LieferPlatz.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public Customer createCustomer(Customer customer, String buildingNumber) {
        customer.setBuildingNumber(buildingNumber);
        return customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(id).orElse(null);

        if (existingCustomer != null) {
            // Update the fields you want to allow modification
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setCity(updatedCustomer.getCity());
            existingCustomer.setStreet(updatedCustomer.getStreet());
            existingCustomer.setBuildingNumber(updatedCustomer.getBuildingNumber());
            existingCustomer.setZipCode(updatedCustomer.getZipCode());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            customerRepository.save(existingCustomer);
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer authenticateCustomer(String email, String password) {

        //
        return customerRepository.findByEmailAndPassword(email, password);
    }
}
