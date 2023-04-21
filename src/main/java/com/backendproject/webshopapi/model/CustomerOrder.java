package com.backendproject.webshopapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    @Cascade(CascadeType.ALL)
    private List<OrderEntry> orderEntries;

    public CustomerOrder(LocalDate date) {
        this.date = date;
    }

    public void addOrderEntry(OrderEntry orderEntry) {
        orderEntries.add(orderEntry);
    }


}
