package com.backendproject.webshopapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private List<OrderEntry> orderEntries = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    public CustomerOrder(LocalDate date) {
        this.date = date;
    }

    public void addOrderEntry(OrderEntry orderEntry) {

        orderEntries.stream()
                .filter(e -> e.getItem().getId() == orderEntry.getItem().getId())
                .findFirst().ifPresentOrElse(e -> e.setQuantity(e.getQuantity() + orderEntry.getQuantity()), () -> orderEntries.add(orderEntry));

    }


}
