package com.backendproject.webshopapi;

import com.backendproject.webshopapi.model.Customer;
import com.backendproject.webshopapi.model.CustomerOrder;
import com.backendproject.webshopapi.model.OrderEntry;
import com.backendproject.webshopapi.model.Product;
import com.backendproject.webshopapi.repository.CustomerOrderRepository;
import com.backendproject.webshopapi.repository.CustomerRepository;
import com.backendproject.webshopapi.repository.OrderEntryRepository;
import com.backendproject.webshopapi.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class WebshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebshopApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner initDb(CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository, OrderEntryRepository orderEntryRepository, ProductRepository productRepository) {
        return (args) -> {

            customerRepository.saveAll(List.of(
                    new Customer("Klas", "Klättermus", "8205287716"),
                    new Customer("Pelle", "Påskhare", "9405295516"),
                    new Customer("Kalle", "Kanin", "8103215417"),
                    new Customer("Musse", "Pigg", "4703084413")
            ));

            productRepository.saveAll(List.of(
                    new Product("Stavmixer", 89.9),
                    new Product("Kaffebryggare", 199.9),
                    new Product("Vattenkokare", 49.9),
                    new Product("Vitlökspress", 19.9)
            ));

            Customer customer1 = customerRepository.findById(1L).get();
            customer1.newOrder(new CustomerOrder(LocalDate.now()));
            customerRepository.save(customer1);

            Customer customer2 = customerRepository.findById(3L).get();
            customer2.newOrder(new CustomerOrder(LocalDate.now()));
            customer2.newOrder(new CustomerOrder(LocalDate.now()));
            customerRepository.save(customer2);


            CustomerOrder order1 = customerOrderRepository.findById(1L).get();
            order1.addOrderEntry(new OrderEntry(productRepository.findById(4L).get(), 3));
            customerOrderRepository.save(order1);

            CustomerOrder order2 = customerOrderRepository.findById(2L).get();
            order2.addOrderEntry(new OrderEntry(productRepository.findById(2L).get(), 5));
            order2.addOrderEntry(new OrderEntry(productRepository.findById(3L).get(), 2));
            customerOrderRepository.save(order2);

        };
    }

}
