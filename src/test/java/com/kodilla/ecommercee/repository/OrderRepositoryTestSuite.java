package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderRepositoryTestSuite {

    @Autowired
    private OrderRepository orderRepository;

    @AfterEach
    public void cleanUp() {
        orderRepository.deleteAll();
    }

    @Test
    void testOrderRepositorySave() {
        //Given
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(BigDecimal.valueOf(1000));
        order.setUserId(3);

        //When
        orderRepository.save(order);
        int id = order.getId();

        //Then
        assertEquals(1,id);

    }

    @Test
    void testOrderRepositoryGetAllOrders() {
        //Given
        Order order = new Order();
        Order order2 = new Order();
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(BigDecimal.valueOf(1000));
        order.setUserId(3);
        order2.setOrderDate(LocalDate.now());
        order2.setTotalPrice(BigDecimal.valueOf(2000));
        order2.setUserId(2);
        orderRepository.save(order);
        orderRepository.save(order2);

        //When
        List<Order> all = orderRepository.findAll();
        int size = all.size();

        //Then
        assertEquals(2,size);

    }

    @Test
    void testOrderRepositoryGetOrder() {
        //Given
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(BigDecimal.valueOf(1000));
        order.setUserId(3);
        Order savedOrder = orderRepository.save(order);

        //When
        Order orderById = orderRepository.findById(savedOrder.getId());

        //Then
        assertEquals(1000, orderById.getTotalPrice().intValue());
        assertEquals(3, orderById.getUserId());

    }

    @Test
    void testOrderRepositoryDeleteOrder() {
        //Given
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(BigDecimal.valueOf(1000));
        order.setUserId(3);
        orderRepository.save(order);

        //When
        orderRepository.deleteById(order.getId());

        //Then
        assertEquals(0, orderRepository.findAll().size());

    }

    @Test
    void testOrderRepositoryUpdateOrder() {
        //Given
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setTotalPrice(BigDecimal.valueOf(1000));
        order.setUserId(3);
        orderRepository.save(order);

        //When
        Order updateOrder = orderRepository.findById(order.getId());
        updateOrder.setTotalPrice(BigDecimal.valueOf(5000));
        orderRepository.save(updateOrder);

        List<Order> all = orderRepository.findAll();

        //Then
        assertEquals(5000, updateOrder.getTotalPrice().intValue());
        assertEquals(1, all.size());

    }



}
