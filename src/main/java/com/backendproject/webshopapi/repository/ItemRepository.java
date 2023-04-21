package com.backendproject.webshopapi.repository;

import com.backendproject.webshopapi.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
