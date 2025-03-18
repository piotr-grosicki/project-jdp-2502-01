package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderRepositoryTestSuite {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void cleanUp() {
        orderRepository.deleteAll();
    }

    @Test
    void testOrderRepositorySave() {
        //Given
        List<Product> products = new ArrayList<>();
        Group group = new Group(1L, "group", products);
        Product product = new Product(1, "product", "description", BigDecimal.valueOf(1000), group);
        products.add(product);
        groupRepository.save(group);
        productRepository.save(product);

        Order order = new Order(1, LocalDate.now(), 3, BigDecimal.valueOf(1000), products);

        //When
        orderRepository.save(order);
        int id = order.getId();

        //Then
        assertEquals(1,id);

    }

    @Test
    void testOrderRepositoryGetAllOrders() {
        //Given
        List<Product> products = new ArrayList<>();
        Group group = new Group(1L, "group", products);
        Product product = new Product(1, "product", "description", BigDecimal.valueOf(1000), group);
        products.add(product);
        groupRepository.save(group);
        productRepository.save(product);

        Order order = new Order(1, LocalDate.now(), 3, BigDecimal.valueOf(1000), products);
        Order order2 = new Order(2, LocalDate.now(), 4, BigDecimal.valueOf(1000), products);

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
        List<Product> products = new ArrayList<>();
        Group group = new Group(1L, "group", products);
        Product product = new Product(1, "product", "description", BigDecimal.valueOf(1000), group);
        products.add(product);
        groupRepository.save(group);
        productRepository.save(product);

        Order order = new Order(1, LocalDate.now(), 3, BigDecimal.valueOf(1000), products);
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
        List<Product> products = new ArrayList<>();
        Group group = new Group(1L, "group", products);
        Product product = new Product(1, "product", "description", BigDecimal.valueOf(1000), group);
        products.add(product);
        groupRepository.save(group);
        productRepository.save(product);

        Order order = new Order(1, LocalDate.now(), 3, BigDecimal.valueOf(1000), products);
        orderRepository.save(order);

        //When
        orderRepository.deleteById(order.getId());

        //Then
        assertEquals(0, orderRepository.findAll().size());

    }

    @Test
    void testOrderRepositoryUpdateOrder() {
        //Given
        List<Product> products = new ArrayList<>();
        Group group = new Group(1L, "group", products);
        Product product = new Product(1, "product", "description", BigDecimal.valueOf(1000), group);
        products.add(product);
        groupRepository.save(group);
        productRepository.save(product);

        Order order = new Order(1, LocalDate.now(), 3, BigDecimal.valueOf(1000), products);
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
