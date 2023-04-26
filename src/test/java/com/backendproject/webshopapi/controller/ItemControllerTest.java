package com.backendproject.webshopapi.controller;

import com.backendproject.webshopapi.model.Customer;
import com.backendproject.webshopapi.model.CustomerOrder;
import com.backendproject.webshopapi.model.OrderEntry;
import com.backendproject.webshopapi.repository.CustomerOrderRepository;
import com.backendproject.webshopapi.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.backendproject.webshopapi.model.Item;
import com.backendproject.webshopapi.repository.ItemRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRepository mockItemRepo;
    @MockBean
    private CustomerRepository mockCustRepo;
    @MockBean
    private CustomerOrderRepository mockCustOrderRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void inti() {
       Item i1 = new Item(1L,"Vitlökspress", 19.9);
       Item i2 = new Item(2L, "Brödrost", 299.9);
       Item i3 = new Item(3L, "Mikrovågsugn", 699.9);

       Customer cust1 = new Customer(1L, "Olle", "Olsson", "8001", null);
       Customer cust2 = new Customer(2L, "Pelle", "Persson", "8002", null);

       CustomerOrder co1 =
               new CustomerOrder(1L, LocalDate.parse("2022-11-23"),
                       List.of(new OrderEntry(1L,2,i1)),cust1);

        CustomerOrder co2 =
                new CustomerOrder(2L, LocalDate.parse("2022-10-10"),
                        List.of(new OrderEntry(2L,2,i2)),cust2);


        when(mockItemRepo.findById(1L)).thenReturn(Optional.of(i1));
        when(mockItemRepo.findById(2L)).thenReturn(Optional.of(i2));
        when(mockItemRepo.findById(3L)).thenReturn(Optional.of(i3));
        when(mockItemRepo.findAll()).thenReturn(Arrays.asList(i1, i2, i3));
        when(mockCustOrderRepo.findById(1L)).thenReturn(Optional.of(co1));
        when(mockCustOrderRepo.findById(2L)).thenReturn(Optional.of(co2));
        when(mockCustRepo.findById(1L)).thenReturn(Optional.of(cust1));
        when(mockCustRepo.findById(2L)).thenReturn(Optional.of(cust2));

    }


    @Test
    void getAllItems() throws Exception {
        List<Item> items = List.of(new Item(1L,"Vitlökspress", 19.9),
                                    new Item(2L, "Brödrost", 299.9),
                                    new Item(3L, "Mikrovågsugn", 699.9));
        String jsonString = objectMapper.writeValueAsString(items);
        this.mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }


    @Test
    public void getItemById() throws Exception{
        Item item = new Item(1L,"Vitlökspress", 19.9);
        String jsonString = objectMapper.writeValueAsString(item);
        this.mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }


    @Test
    void createItemPost_shouldReturnOK() throws Exception {
        this.mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":4,\"name\":\"testKastrull\",\"price\":599.9}"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Product added")));
    }

    @Test
    void createItemPost_nameMissing() throws Exception {
        this.mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":4,\"name\":\"\",\"price\":599.9}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo("Invalid product name")));
    }

    @Test
    void createItemPost_errPrice() throws Exception {
        this.mockMvc.perform(post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":4,\"name\":\"testKastrull\",\"price\":-15.5}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo("Price must be 0 or more")));
    }


    @Test
    void buyItem_nonExistingItemId() throws Exception {
        this.mockMvc.perform(post("/items/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"orderId\":1,\"itemId\":99,\"quantity\":2}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo("Product not found")));
    }


    @Test
    void buyItem_nonExistingCustomerId() throws Exception {
        this.mockMvc.perform(post("/items/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":99,\"orderId\":1,\"itemId\":1,\"quantity\":2}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo("Customer not found")));
    }


    @Test
    void buyItem_nonMatchingCustomerOrder() throws Exception {
        this.mockMvc.perform(post("/items/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"orderId\":2,\"itemId\":2,\"quantity\":2}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo("Customer does not own order")));
    }


    @Test
    void buyItem_Success() throws Exception {
        this.mockMvc.perform(post("/items/buy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":2,\"orderId\":2,\"itemId\":2,\"quantity\":2}"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Product added to order #2")));
    }


}
