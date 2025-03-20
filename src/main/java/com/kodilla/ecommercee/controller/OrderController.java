package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
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
    public OrderDto getOrder(@PathVariable long id) {
        return new OrderDto(1L, LocalDate.now(), 1L, new BigDecimal("500.20"), new ArrayList<>());
    }

    @DeleteMapping(value = "{id}")
    public void deleteOrder(@PathVariable int id) {
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody OrderDto orderDto) {
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        return new OrderDto(5L, LocalDate.now(),1L, new BigDecimal("600"), new ArrayList<>());
    }

}
