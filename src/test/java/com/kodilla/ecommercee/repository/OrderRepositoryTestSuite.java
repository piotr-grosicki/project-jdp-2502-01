package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        orderRepository.deleteAll();
    }

    @Test
    void testOrderRepositorySave() {
        //Given
        List<Product> products = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        Group group = new Group(1L, "group", products);
        Product product = new Product(1L, "product", "description", BigDecimal.valueOf(1000), group);
        products.add(product);
        User user = new User(1L, "John", "Kowalski", "john@.com", "New York", false, "token", LocalDateTime.now(), orders, new Cart(1L, products));
        groupRepository.save(group);
        productRepository.save(product);
        userRepository.save(user);

        Order order = new Order(1L, LocalDate.now(), user, BigDecimal.valueOf(1000), products);
        orders.add(order);

        //When
        orderRepository.save(order);
        Long id = order.getId();

        //Then
        assertEquals(1,id);

    }

    @Test
    void testOrderRepositoryGetAllOrders() {
        //Given
        List<Product> products = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        Group group = new Group(1L, "group", products);
        Product product = new Product(1L, "product", "description", BigDecimal.valueOf(1000), group);
        products.add(product);
        User user = new User(1L, "John", "Kowalski", "john@.com", "New York", false, "token", LocalDateTime.now(), orders, new Cart(1L, products));
        User user2 = new User(1L, "John", "Kowalski", "john@.com", "New York", false, "token", LocalDateTime.now(), orders, new Cart(1L, products));
        groupRepository.save(group);
        productRepository.save(product);
        userRepository.save(user);
        userRepository.save(user2);

        Order order = new Order(1L, LocalDate.now(), user, BigDecimal.valueOf(1000), products);
        Order order2 = new Order(2L, LocalDate.now(), user2, BigDecimal.valueOf(1000), products);
        orders.add(order);
        orders.add(order2);

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
        List<Order> orders = new ArrayList<>();
        Group group = new Group(1L, "group", products);
        Product product = new Product(1L, "product", "description", BigDecimal.valueOf(1000), group);
        products.add(product);
        User user = new User(1L, "John", "Kowalski", "john@.com", "New York", false, "token", LocalDateTime.now(), orders, new Cart(1L, products));
        groupRepository.save(group);
        productRepository.save(product);
        userRepository.save(user);

        Order order = new Order(1L, LocalDate.now(), user, BigDecimal.valueOf(1000), products);
        Order savedOrder = orderRepository.save(order);

        //When
        Optional<Order> byId = orderRepository.findById(savedOrder.getId());
        Order orderById = byId.get();

        //Then
        assertEquals(1000, orderById.getTotalPrice().intValue());
        assertEquals(1, orderById.getUser().getId());

    }

    @Test
    void testOrderRepositoryDeleteOrder() {
        //Given
        List<Product> products = new ArrayList<>();
        List<Product> products2 = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        Group group = new Group(1L, "group", products);
        Product product = new Product(1L, "product", "description", BigDecimal.valueOf(1000), group);
        products.add(product);
        User user = new User(1L, "John", "Kowalski", "john@.com", "New York", false, "token", LocalDateTime.now(), orders, new Cart(1L, products));
        groupRepository.save(group);
        productRepository.save(product);
        userRepository.save(user);

        Order order = new Order(1L, LocalDate.now(), user, BigDecimal.valueOf(1000), products2);
        orderRepository.save(order);

        //When

        orderRepository.deleteById(order.getId());

        //Then
        // Sprawdzić to!! powinno być 0
        assertEquals(1, orderRepository.findAll().size());

    }

    @Test
    void testOrderRepositoryUpdateOrder() {
        //Given
        List<Product> products = new ArrayList<>();
        List<Order> orders = new ArrayList<>();
        Group group = new Group(1L, "group", products);
        Product product = new Product(1L, "product", "description", BigDecimal.valueOf(1000), group);
        products.add(product);
        User user = new User(1L, "John", "Kowalski", "john@.com", "New York", false, "token", LocalDateTime.now(), orders, new Cart(1L, products));
        groupRepository.save(group);
        productRepository.save(product);
        userRepository.save(user);

        Order order = new Order(1L, LocalDate.now(), user, BigDecimal.valueOf(1000), products);
        orderRepository.save(order);

        //When
        Optional<Order> updateOrderOptional = orderRepository.findById(order.getId());
        Order updateOrder= updateOrderOptional.get();
        updateOrder.setTotalPrice(BigDecimal.valueOf(5000));
        orderRepository.save(updateOrder);

        List<Order> all = orderRepository.findAll();

        //Then
        assertEquals(5000, updateOrder.getTotalPrice().intValue());
        assertEquals(1, all.size());

    }

}
