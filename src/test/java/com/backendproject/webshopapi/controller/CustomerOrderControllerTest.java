package com.backendproject.webshopapi.controller;

import com.backendproject.webshopapi.model.Customer;
import com.backendproject.webshopapi.model.CustomerOrder;
import com.backendproject.webshopapi.model.Item;
import com.backendproject.webshopapi.model.OrderEntry;
import com.backendproject.webshopapi.repository.CustomerOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerOrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerOrderRepository mockRepo;

	@BeforeEach
	public void init() {

		var customer1 = new Customer(1L, "kalle", "anka", "123", null);
		var customer2 = new Customer(2L, "musse", "pigg", "456", null);

		var orders = List.of(
				new CustomerOrder(1L, LocalDate.parse("2022-04-07"), List.of(
						new OrderEntry(1L, 2, new Item(23L, "Multikokare", 1499.9)))
						, customer1),
				new CustomerOrder(2L, LocalDate.parse("2023-03-04"), List.of(
						new OrderEntry(2L, 2, new Item(2L, "Kaffebryggare", 199.9)),
						new OrderEntry(3L, 2, new Item(6L, "Mikrovågsugn", 699.9)),
						new OrderEntry(4L, 3, new Item(15L, "Ugn", 2999.9)))
						, customer2),
				new CustomerOrder(3L, LocalDate.parse("2022-01-12"), List.of(
						new OrderEntry(5L, 2, new Item(1L, "Stavmixer", 89.9)),
						new OrderEntry(6L, 1, new Item(28L, "Espressomaskin", 2999.9)),
						new OrderEntry(7L, 3, new Item(3L, "Vattenkokare", 49.9)),
						new OrderEntry(8L, 5, new Item(17L, "Mixer", 499.9))
						), customer1)
		);

		when(mockRepo.findAll()).thenReturn(orders);
		when(mockRepo.findByCustomerId(1L)).thenReturn(List.of(orders.get(0), orders.get(2)));
		when(mockRepo.findByCustomerId(2L)).thenReturn(List.of(orders.get(1)));

	}


	@Test
	public void getAllOrders() throws Exception {
		var ordersJson = """
			[{"id":1,"date":"2022-04-07","orderEntries":[{"id":1,"quantity":2,"item":{"id":23,"name":"Multikokare","price":1499.9}}]},{"id":2,"date":"2023-03-04","orderEntries":[{"id":2,"quantity":2,"item":{"id":2,"name":"Kaffebryggare","price":199.9}},{"id":3,"quantity":2,"item":{"id":6,"name":"Mikrovågsugn","price":699.9}},{"id":4,"quantity":3,"item":{"id":15,"name":"Ugn","price":2999.9}}]},{"id":3,"date":"2022-01-12","orderEntries":[{"id":5,"quantity":2,"item":{"id":1,"name":"Stavmixer","price":89.9}},{"id":6,"quantity":1,"item":{"id":28,"name":"Espressomaskin","price":2999.9}},{"id":7,"quantity":3,"item":{"id":3,"name":"Vattenkokare","price":49.9}},{"id":8,"quantity":5,"item":{"id":17,"name":"Mixer","price":499.9}}]}]""";

		mockMvc.perform(get("/api/orders"))
				.andExpect(status().isOk())
				.andExpect(content().json(ordersJson));
	}

	@Test
	public void getOrdersByCustomerId() throws Exception {
		var ordersJson = """
						[{"id":1,"date":"2022-04-07","orderEntries":[{"id":1,"quantity":2,"item":{"id":23,"name":"Multikokare","price":1499.9}}]},{"id":3,"date":"2022-01-12","orderEntries":[{"id":5,"quantity":2,"item":{"id":1,"name":"Stavmixer","price":89.9}},{"id":6,"quantity":1,"item":{"id":28,"name":"Espressomaskin","price":2999.9}},{"id":7,"quantity":3,"item":{"id":3,"name":"Vattenkokare","price":49.9}},{"id":8,"quantity":5,"item":{"id":17,"name":"Mixer","price":499.9}}]}]""";

		mockMvc.perform(get("/api/orders/1"))
				.andExpect(status().isOk())
				.andExpect(content().json(ordersJson));
	}

}
