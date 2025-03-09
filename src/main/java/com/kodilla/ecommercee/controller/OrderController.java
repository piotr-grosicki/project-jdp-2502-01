package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.OrderDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ecommercee")
public class OrderController {

    @GetMapping
    public List<OrderDto> getOrders() {
        return new ArrayList<>();
    }

    @GetMapping(value = "{id}")
    public OrderDto getOrder(@PathVariable Long id) {
        return new OrderDto(1L, 2, 500.20);
    }

    @DeleteMapping()
    public void deleteOrder(Long orderId) {
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createOrder(@RequestBody OrderDto orderDto) {
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        return new OrderDto(5, 3, 600);
    }

}
