package com.backendproject.webshopapi.controller;

import com.backendproject.webshopapi.model.Customer;
import com.backendproject.webshopapi.model.CustomerOrder;
import com.backendproject.webshopapi.model.Item;
import com.backendproject.webshopapi.repository.CustomerOrderRepository;
import com.backendproject.webshopapi.repository.CustomerRepository;
import com.backendproject.webshopapi.repository.ItemRepository;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class ThymeController {

    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final CustomerOrderRepository orderRepository;

    public ThymeController(CustomerRepository customerRepository, ItemRepository itemRepository, CustomerOrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }


    @RequestMapping({"/", "/index"})
    public String index() {
        return "index.html";
    }


    @RequestMapping("/customers")
    public String getAllCustomers(Model model) {
        Iterable<Customer> c = customerRepository.findAll();
        model.addAttribute("allCustomersList", c);
        model.addAttribute("firstNameTitle", "firstName");
        model.addAttribute("lastNameTitle", "lastName");
        model.addAttribute("ssnTitle", "ssn");
        model.addAttribute("customerTitle", "ALL CUSTOMERS");
        return "allcustomers.html";
    }

    @RequestMapping("/items")
    public String getAllItems(Model model) {
        Iterable<Item> i = itemRepository.findAll();
        model.addAttribute("allItemsList", i);
        model.addAttribute("nameTitle", "name");
        model.addAttribute("priceTitle", "price");
        model.addAttribute("itemTitle", "ALL ITEMS");
        return "allitems.html";
    }

    @RequestMapping("/orders")
    public String getAllOrders(
            @RequestParam(required = false, defaultValue = "-1") long customerId,
            @RequestParam(required = false, defaultValue = "id") String sortby,
            @RequestParam(required = false, defaultValue = "asc") String order,
            Model model) {

        List<CustomerOrder> orders;

        if (customerId > 0) {
            orders = orderRepository.findByCustomerId(customerId);
            model.addAttribute("orderTitle", "ORDERS FOR CUSTOMER ID: " + customerId);
        } else {
            orders = orderRepository.findAll();
            model.addAttribute("orderTitle", "ALL ORDERS");
        }

        if (sortby.equals("id")) {
            orders.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
        } else if (sortby.equals("date")) {
            orders.sort(Comparator.comparing(CustomerOrder::getDate));
        } else if (sortby.equals("total")) {
            orders.sort(Comparator.comparing(CustomerOrder::getTotal));
        } else if (sortby.equals("items")) {
            orders.sort(Comparator.comparing(CustomerOrder::getItems));
        } else if (sortby.equals("customer")) {
            orders.sort((o1, o2) -> (int) (o1.getCustomer().getId() - o2.getCustomer().getId()));
        }

        if (order.equals("desc")) {
            Collections.reverse(orders);
        }

        String newOrder;

        if (order.equals("asc")) {
            newOrder = "desc";
        } else {
            newOrder = "asc";
        }

        model.addAttribute("customerId", customerId);
        model.addAttribute("sortby", sortby);
        model.addAttribute("newOrder", newOrder);
        model.addAttribute("allOrdersList", orders);
        model.addAttribute("idTitle", "id");
        model.addAttribute("dateTitle", "date");
        return "allorders.html";
    }

    @RequestMapping("/order")
    public String getOrder(@RequestParam long orderId, Model model) {
        CustomerOrder order = orderRepository.findById(orderId).orElse(null);
        model.addAttribute("order", order);
        model.addAttribute("orderTitle", "ORDER ID: " + orderId);
        return "order.html";
    }

    @RequestMapping("/confirmitem")
    public String confirmItem(Model model, Item i, String message) {
        model.addAttribute("item", i);
        model.addAttribute("message", message);
        return "confirmitem.html";
    }

    @GetMapping("/addcustomer")
    public String addCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "addcustomer.html";
    }

    @GetMapping("/registercustomer")
    public String addCustomer(@RequestParam String fname, @RequestParam String lname,
                              @RequestParam String ssn, Model model) {
        Customer c = new Customer();
        c.setFirstName(fname);
        c.setLastName(lname);
        c.setSsn(ssn);
        customerRepository.save(c);
        return getAllCustomers(model);
    }

    @GetMapping("/additem")
    public String addItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "additem.html";
    }

    @GetMapping("/registeritem")
    public String addItem(@RequestParam String name, @RequestParam Double price, Model model) {

        if (name.isBlank() || price < 0) return confirmItem(model, null, "Error: invalid data");
        Item i = new Item();
        i.setName(name);
        i.setPrice(price);
        itemRepository.save(i);
        return confirmItem(model, i, "Item successfully added");
    }


}
