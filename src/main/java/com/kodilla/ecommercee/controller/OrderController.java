package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;

import com.kodilla.ecommercee.domain.Product;

import com.kodilla.ecommercee.domain.User;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ecommercee/orders")
public class OrderController {

    @GetMapping
    public List<OrderDto> getOrders() {
        return new ArrayList<>();
    }

    @GetMapping(value = "{id}")

    public OrderDto getOrder(@PathVariable int id) {
        List<Product> products = new ArrayList<>();
        return new OrderDto(1L, LocalDate.now(), new User(), new BigDecimal(1000), products);
    }

    @DeleteMapping(value = "{id}")
    public void deleteOrder(@PathVariable int id) {
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody OrderDto orderDto) {
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {

        List<Product> products = new ArrayList<>();
        return new OrderDto(1L, LocalDate.now(), new User(), new BigDecimal(1000), products);
    }

}
