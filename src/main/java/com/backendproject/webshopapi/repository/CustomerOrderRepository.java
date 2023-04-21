package com.backendproject.webshopapi.repository;

import com.backendproject.webshopapi.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}
