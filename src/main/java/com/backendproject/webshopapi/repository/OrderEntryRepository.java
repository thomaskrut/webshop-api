package com.backendproject.webshopapi.repository;

import com.backendproject.webshopapi.model.OrderEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEntryRepository extends JpaRepository<OrderEntry, Long> {
}
