package com.backendproject.webshopapi.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class OrderEntry {

    @Id
    @GeneratedValue
    private long id;

    private int quantity;

    @ManyToOne
    @JoinColumn
    private Product product;

    public OrderEntry(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}
