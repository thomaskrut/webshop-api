package com.backendproject.webshopapi.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Customer {

    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private String ssn;

    @OneToMany
    @JoinColumn(name = "customer_id")
    private List<CustomerOrder> orders;

    public Customer(String firstName, String lastName, String ssn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
    }

    public void newOrder(CustomerOrder order) {
        orders.add(order);
    }


}
