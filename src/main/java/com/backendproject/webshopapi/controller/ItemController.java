package com.backendproject.webshopapi.controller;

import com.backendproject.webshopapi.model.*;
import com.backendproject.webshopapi.repository.CustomerOrderRepository;
import com.backendproject.webshopapi.repository.CustomerRepository;
import com.backendproject.webshopapi.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class ItemController {


    private final ItemRepository itemRepository;
    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerRepository customerRepository;

    public ItemController(ItemRepository itemRepository, CustomerOrderRepository customerOrderRepository, CustomerRepository customerRepository) {
        this.itemRepository = itemRepository;
        this.customerOrderRepository = customerOrderRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/items")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable long id) {
        return itemRepository.findById(id).get();
    }

    @PostMapping("/items")
    public ResponseEntity<String> createItem(@RequestBody Item item) {

        if (item.getName().isBlank()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid product name");

        if (item.getPrice() < 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price must be 0 or more");

        itemRepository.save(item);
        return ResponseEntity.ok("Product added");
    }

    @PostMapping("/items/buy")
    public ResponseEntity<String> buyItem(@RequestBody AddItemRequest request) {

        Item item = itemRepository.findById(request.getItemId()).orElse(null);
        if (item == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found");

        Customer customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        if (customer == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer not found");

        CustomerOrder order = customerOrderRepository.findById(request.getOrderId()).orElse(null);
        if (order != null) {
            if (order.getCustomer().getId() != request.getCustomerId()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Customer does not own order");
        } else {
            order = customer.addNewOrder(new CustomerOrder(LocalDate.now()));
        }

        order.addOrderEntry(new OrderEntry(item, request.getQuantity()));

        customerOrderRepository.save(order);

        return ResponseEntity.status(HttpStatus.OK).body("Product added to order #" + order.getId());
    }


}
