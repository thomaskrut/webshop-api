package com.backendproject.webshopapi.controller;

import com.backendproject.webshopapi.model.Item;
import com.backendproject.webshopapi.repository.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

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
public class ItemControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRepository mockRepo;

    @BeforeEach
    public void inti() {
       Item i1 = new Item(1L,"Vitlökspress", 19.9);
       Item i2 = new Item(2L, "Brödrost", 299.9);
       Item i3 = new Item(3L, "Mikrovågsugn", 699.9);

        when(mockRepo.findById(1L)).thenReturn(Optional.of(i1));
        when(mockRepo.findById(2L)).thenReturn(Optional.of(i2));
        when(mockRepo.findById(3L)).thenReturn(Optional.of(i3));
        when(mockRepo.findAll()).thenReturn(Arrays.asList(i1, i2, i3));

    }

    @Test
    void getAllItems() throws Exception {
        System.out.println(this.mockMvc.perform(get("/items")));
        this.mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Vitlökspress\",\"price\":19.9},"+
                        "{\"id\":2,\"name\":\"Brödrost\",\"price\":299.9},"+
                        "{\"id\":3,\"name\":\"Mikrovågsugn\",\"price\":699.9}]"));
    }


    @Test
    public void getItemById() throws Exception{
        this.mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Vitlökspress\",\"price\":19.9}"));
    }
/*
    @Test
    public void getItemById() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Item item = new Item(1L,"Vitlökspress", 19.9);
        String jsonString = objectMapper.writeValueAsString(item);
        this.mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonString));
    }*/
}
