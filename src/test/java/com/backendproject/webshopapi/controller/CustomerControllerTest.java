package com.backendproject.webshopapi.controller;

import com.backendproject.webshopapi.model.Customer;
import com.backendproject.webshopapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CustomerRepository mockRepo;

        @BeforeEach
        public void inti() {
                Customer c1 = new Customer(1L,"kalle","anka","123", null);
                Customer c2 = new Customer(2L,"musse","pigg","456", null);
                Customer c3 = new Customer(3L,"kajsa","anka","789", null);

                when(mockRepo.findById(1L)).thenReturn(Optional.of(c1));
                when(mockRepo.findById(2L)).thenReturn(Optional.of(c2));
                when(mockRepo.findById(3L)).thenReturn(Optional.of(c3));
                when(mockRepo.findAll()).thenReturn(Arrays.asList(c1, c2, c3));
        }

        @Test
        void getAllCustomers() throws Exception {
                System.out.println(this.mockMvc.perform(get("/customers")));
                this.mockMvc.perform(get("/customers"))
                        .andExpect(status().isOk())
                        .andExpect(content().json("[{\"firstName\":\"kalle\",\"lastName\":\"anka\",\"ssn\":\"123\",\"orders\":null,\"id\":1}," +
                                "{\"firstName\":\"musse\",\"lastName\":\"pigg\",\"ssn\":\"456\",\"orders\":null,\"id\":2}," +
                                "{\"firstName\":\"kajsa\",\"lastName\":\"anka\",\"ssn\":\"789\",\"orders\":null,\"id\":3}]"));
        }

        @Test
        void getCustomerById() throws Exception {
                this.mockMvc.perform(get("/customers/1"))
                        .andExpect(status().isOk())
                        .andExpect(content().json("{\"firstName\":\"kalle\",\"lastName\":\"anka\",\"ssn\":\"123\",\"orders\":null,\"id\":1}"));
        }
}