package com.backendproject.webshopapi.repository;

import com.backendproject.webshopapi.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
