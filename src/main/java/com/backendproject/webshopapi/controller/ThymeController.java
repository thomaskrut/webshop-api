package com.backendproject.webshopapi.controller;

import com.backendproject.webshopapi.model.Customer;
import com.backendproject.webshopapi.model.CustomerOrder;
import com.backendproject.webshopapi.model.Item;
import com.backendproject.webshopapi.repository.CustomerOrderRepository;
import com.backendproject.webshopapi.repository.CustomerRepository;
import com.backendproject.webshopapi.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path="/thyme")
public class ThymeController {

    private CustomerRepository customerRepository;
    private ItemRepository itemRepository;
    private CustomerOrderRepository orderRepository;

    public ThymeController(CustomerRepository customerRepository, ItemRepository itemRepository, CustomerOrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }


    @RequestMapping("/customers")
    public String getAllCustomers(Model model) {
        Iterable<Customer> c = customerRepository.findAll();
        model.addAttribute("allCustomersList", c);
        model.addAttribute("firstNameTitle", "firstName");
        model.addAttribute("lastNameTitle", "lastName");
        model.addAttribute("ssnTitle", "ssn");
        model.addAttribute("customerTitle", "All Customers");
        return "allcustomers.html";
    }

    @RequestMapping("/items")
    public String getAllItems(Model model) {
        Iterable<Item> i = itemRepository.findAll();
        model.addAttribute("allItemsList", i);
        model.addAttribute("nameTitle", "name");
        model.addAttribute("priceTitle", "price");
        model.addAttribute("itemTitle", "All Items");
        return "allitems.html";
    }

    @RequestMapping("/orders")
    public String getAllOrders(Model model) {
        Iterable<CustomerOrder> orders = orderRepository.findAll();
        model.addAttribute("allOrdersList", orders);
        model.addAttribute("idTitle", "id");
        model.addAttribute("dateTitle", "date");
        model.addAttribute("orderTitle", "All Orders");
        return "allorders.html";
    }

    @GetMapping("/addcustomer")
    public String addCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "addcustomer.html";
    }

    @GetMapping(path = "/registercustomer")
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

    @GetMapping(path = "/registeritem")
    public String addItem(@RequestParam String name, @RequestParam Double price, Model model) {
        Item i = new Item();
        i.setName(name);
        i.setPrice(price);
        itemRepository.save(i);
        return getAllItems(model);
    }



}