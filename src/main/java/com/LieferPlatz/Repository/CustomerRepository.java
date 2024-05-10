package com.LieferPlatzProject.LieferPlatz.Repository;

import com.LieferPlatzProject.LieferPlatz.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmailAndPassword(String email, String password);
}
