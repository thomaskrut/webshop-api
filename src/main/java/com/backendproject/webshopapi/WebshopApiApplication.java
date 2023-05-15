package com.backendproject.webshopapi;

import com.backendproject.webshopapi.model.Customer;
import com.backendproject.webshopapi.model.CustomerOrder;
import com.backendproject.webshopapi.model.OrderEntry;
import com.backendproject.webshopapi.model.Item;
import com.backendproject.webshopapi.repository.CustomerOrderRepository;
import com.backendproject.webshopapi.repository.CustomerRepository;
import com.backendproject.webshopapi.repository.OrderEntryRepository;
import com.backendproject.webshopapi.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@SpringBootApplication
public class WebshopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebshopApiApplication.class, args);
    }

    /*
    @Bean
    public CommandLineRunner initDb(CustomerRepository customerRepository, CustomerOrderRepository customerOrderRepository, OrderEntryRepository orderEntryRepository, ItemRepository itemRepository) {
        return (args) -> {

            var newCustomers = List.of(
                    new Customer("Klas", "Klättermus", "8205287716"),
                    new Customer("Pelle", "Påskhare", "9405295516"),
                    new Customer("Kalle", "Kanin", "8103215417"),
                    new Customer("Musse", "Pigg", "4703084413"),
                    new Customer("Lina", "Lejon", "8312165123"),
                    new Customer("Erik", "Ekorre", "8911016814"),
                    new Customer("Maja", "Mård", "9005154725"),
                    new Customer("Oscar", "Orm", "9207278516"),
                    new Customer("Hugo", "Häst", "9512029817"),
                    new Customer("Ella", "Elefant", "9806113418"),
                    new Customer("Bella", "Björn", "8506208219"),
                    new Customer("David", "Delfin", "8707121020"),
                    new Customer("Fanny", "Falk", "9102185721"),
                    new Customer("Göran", "Giraff", "8808112822"),
                    new Customer("Ida", "Iller", "9604016423")
            );

            customerRepository.saveAll(newCustomers);

            var newItems = List.of(
                    new Item("Stavmixer", 89.9),
                    new Item("Kaffebryggare", 199.9),
                    new Item("Vattenkokare", 49.9),
                    new Item("Vitlökspress", 19.9),
                    new Item("Brödrost", 299.9),
                    new Item("Mikrovågsugn", 699.9),
                    new Item("Kylskåp", 3999.9),
                    new Item("Diskmaskin", 3499.9),
                    new Item("Torrställ", 159.9),
                    new Item("Köksvåg", 69.9),
                    new Item("Kökskniv", 129.9),
                    new Item("Skärbräda", 39.9),
                    new Item("Stekpanna", 249.9),
                    new Item("Kastrull", 199.9),
                    new Item("Ugn", 2999.9),
                    new Item("Köksfläkt", 2499.9),
                    new Item("Mixer", 499.9),
                    new Item("Slow cooker", 599.9),
                    new Item("Kaffekvarn", 299.9),
                    new Item("Frys", 4199.9),
                    new Item("Hushållsassistent", 2299.9),
                    new Item("Riskokare", 399.9),
                    new Item("Multikokare", 1499.9),
                    new Item("Vakuumpackare", 799.9),
                    new Item("Wokpanna", 599.9),
                    new Item("Äggkokare", 99.9),
                    new Item("Citruspress", 199.9),
                    new Item("Espressomaskin", 2999.9),
                    new Item("Smörgåsgrill", 399.9),
                    new Item("Grillpanna", 299.9),
                    new Item("Soppkastrull", 599.9),
                    new Item("Salladsslunga", 149.9),
                    new Item("Matberedare", 1099.9),
                    new Item("Iskubsbehållare", 29.9),
                    new Item("Vinöppnare", 59.9)
            );


            itemRepository.saveAll(newItems);

            var customersInDb = customerRepository.findAll();

            if (customersInDb.size() == 0) {
                return;
            }

            customersInDb.forEach(customer -> {
                for (int i = 0; i < (int) (Math.random() * 5) + 1; i++) {
                    customer.addNewOrder(new CustomerOrder(LocalDate.now().minusDays((int) (Math.random() * 500) + 1)));
                }
                customerRepository.save(customer);
            });

            var ordersInDb = customerOrderRepository.findAll();

            if (ordersInDb.size() == 0) {
                return;
            }

            var itemsInDb = itemRepository.findAll();

            if (itemsInDb.size() == 0) {
                return;
            }

            ordersInDb.forEach(order -> {
                var orderItems = (int) (Math.random() * 5) + 1;
                Supplier<Integer> quantityOfItem = () -> (int) (Math.random() * 3) + 1;

                for (int i = 0; i < orderItems; i++) {
                    var randomItem = itemsInDb.get((int) (Math.random() * itemsInDb.size()));
                    order.addOrderEntry(new OrderEntry(randomItem, quantityOfItem.get()));
                }
                customerOrderRepository.save(order);
            });
        };
    }
*/
}
