package com.backendproject.webshopapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class CustomerOrder {

    @Id
    @GeneratedValue
    private long id;

    private LocalDate date;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<OrderEntry> orderEntries;

    public CustomerOrder(LocalDate date) {
        this.date = date;
    }

    public void addOrderEntry(OrderEntry orderEntry) {
        orderEntries.add(orderEntry);
    }


}
