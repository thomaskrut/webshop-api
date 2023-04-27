package com.backendproject.webshopapi.controller;

import com.backendproject.webshopapi.model.Customer;
import com.backendproject.webshopapi.model.CustomerOrder;
import com.backendproject.webshopapi.model.Item;
import com.backendproject.webshopapi.model.OrderEntry;
import com.backendproject.webshopapi.repository.CustomerOrderRepository;
import com.backendproject.webshopapi.repository.CustomerRepository;
import com.backendproject.webshopapi.repository.ItemRepository;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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

    @RequestMapping("/thyme/index")
    public String getIndex() {
        return "index.html";
    }

    @RequestMapping("/")
    public String redirect() {
        return "redirect:/thyme/index";
    }

    @RequestMapping({"/thyme/customers", "/thyme/addorder"})
    public String getAllCustomers(Model model) {
        Iterable<Customer> c = customerRepository.findAll();
        model.addAttribute("allCustomersList", c);
        model.addAttribute("firstNameTitle", "firstName");
        model.addAttribute("lastNameTitle", "lastName");
        model.addAttribute("ssnTitle", "ssn");
        model.addAttribute("customerTitle", "ALL CUSTOMERS");
        return "allcustomers.html";
    }

    @RequestMapping("/thyme/items")
    public String getAllItems(Model model) {
        Iterable<Item> i = itemRepository.findAll();
        model.addAttribute("allItemsList", i);
        model.addAttribute("nameTitle", "name");
        model.addAttribute("priceTitle", "price");
        model.addAttribute("itemTitle", "ALL ITEMS");
        return "allitems.html";
    }

    @RequestMapping("/thyme/orders")
    public String getAllOrders(
            @RequestParam(required = false, defaultValue = "-1") long customerId,
            @RequestParam(required = false, defaultValue = "id") String sortby,
            @RequestParam(required = false, defaultValue = "desc") String order,
            Model model) {

        List<CustomerOrder> orders;

        if (customerId > 0) {
            orders = orderRepository.findByCustomerId(customerId);
            model.addAttribute("orderTitle", "ORDERS FOR CUSTOMER ID: " + customerId);
        } else {
            orders = orderRepository.findAll();
            model.addAttribute("orderTitle", "ALL ORDERS");
        }

        switch (sortby) {
            case "id" -> orders.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));
            case "date" -> orders.sort(Comparator.comparing(CustomerOrder::getDate));
            case "total" -> orders.sort(Comparator.comparing(CustomerOrder::getTotal));
            case "items" -> orders.sort(Comparator.comparing(CustomerOrder::getItems));
            case "customer" -> orders.sort((o1, o2) -> (int) (o1.getCustomer().getId() - o2.getCustomer().getId()));
        }

        if (order.equals("desc")) {
            Collections.reverse(orders);
        }

        String newOrder = (order.equals("asc")) ? "desc" : "asc";

        model.addAttribute("customerId", customerId);
        model.addAttribute("sortby", sortby);
        model.addAttribute("newOrder", newOrder);
        model.addAttribute("allOrdersList", orders);
        model.addAttribute("idTitle", "id");
        model.addAttribute("dateTitle", "date");
        return "allorders.html";
    }

    @RequestMapping("/thyme/order")
    public String getOrder(@RequestParam long orderId, Model model) {
        CustomerOrder order = orderRepository.findById(orderId).orElse(null);
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("order", order);
        model.addAttribute("orderTitle", "ORDER ID: " + orderId);
        return "order.html";
    }

    @RequestMapping("/thyme/confirmitem")
    public String confirmItem(Model model, Item i, String message) {
        model.addAttribute("item", i);
        model.addAttribute("message", message);
        return "confirmitem.html";
    }

    @GetMapping("/thyme/addcustomer")
    public String addCustomerForm() {
        return "addcustomer.html";
    }

    @GetMapping("/thyme/registercustomer")
    public String addCustomer(@RequestParam String fname, @RequestParam String lname,
                              @RequestParam String ssn, Model model) {
        Customer c = new Customer();
        c.setFirstName(fname);
        c.setLastName(lname);
        c.setSsn(ssn);
        customerRepository.save(c);
        return getAllCustomers(model);
    }

    @GetMapping("/thyme/additem")
    public String addItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "additem.html";
    }

    @GetMapping("/thyme/registeritem")
    public String addItem(@RequestParam String name, @RequestParam Double price, Model model) {

        if (name.isBlank() || price < 0) return confirmItem(model, null, "Error: invalid data");
        Item i = new Item();
        i.setName(name);
        i.setPrice(price);
        itemRepository.save(i);
        return confirmItem(model, i, "Item successfully added");
    }

    @GetMapping("/thyme/adjustorder")
    public String adjustOrder(@RequestParam long orderId,
                              @RequestParam String itemId,
                              @RequestParam int quantity,
                              Model model) {

        long itemIdLong;
        try {
            itemIdLong = Long.parseLong(itemId);
        } catch (NumberFormatException e) {
            return getOrder(orderId, model);
        }

        CustomerOrder order = orderRepository.findById(orderId).orElse(null);
        Item item = itemRepository.findById(itemIdLong).orElse(null);

        if (item == null || order == null) return getOrder(orderId, model);

        order.addOrderEntry(new OrderEntry(item, quantity));
        orderRepository.save(order);
        return getOrder(orderId, model);
    }

    @GetMapping("/thyme/neworder")
    public String newOrder(@RequestParam long customerId, Model model) {
        Customer c = customerRepository.findById(customerId).orElse(null);
        if (c == null) return getAllOrders(-1, "id", "desc", model);
        CustomerOrder order = new CustomerOrder(LocalDate.now());
        orderRepository.save(order);
        c.addNewOrder(order);
        customerRepository.save(c);
        return getOrder(order.getId(), model);

    }



}
