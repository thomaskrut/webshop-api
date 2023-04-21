package com.backendproject.webshopapi.repository;

import com.backendproject.webshopapi.model.OrderCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCollectionRepository extends JpaRepository<OrderCollection, Long> {
}
