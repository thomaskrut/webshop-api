package com.backendproject.webshopapi.controller;

import com.backendproject.webshopapi.model.Customer;
import com.backendproject.webshopapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {


    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable long id) {
        return customerRepository.findById(id).get();
    }

    @PostMapping("/customers")
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {

        if (customer.getFirstName().isBlank() || customer.getLastName().isBlank()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid name");

        if (customer.getSsn().trim().length() != 10) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid SSN");

        if (customerRepository.findAll().stream().anyMatch(c -> c.getSsn().equals(customer.getSsn()))) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("SSN already in database");

        customerRepository.save(customer);

        return ResponseEntity.ok("Customer added to database");
    }

}
