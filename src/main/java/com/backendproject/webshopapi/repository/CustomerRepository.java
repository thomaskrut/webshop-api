package com.backendproject.webshopapi.repository;

import com.backendproject.webshopapi.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
