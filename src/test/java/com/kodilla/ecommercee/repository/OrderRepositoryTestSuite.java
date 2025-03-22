package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import org.junit.jupiter.api.AfterEach;
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
    @Autowired
    private UserRepository userRepository;


    @AfterEach
    public void cleanUp() {
        orderRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    void testOrderRepositorySave() {
        //Given
        Group group = new Group("group");
        Product product = new Product("product", "description", BigDecimal.valueOf(1000), group);
        User user = new User("firstname", "lastname", "email", "address", false);
        groupRepository.save(group);
        productRepository.save(product);
        userRepository.save(user);

        Order order = new Order(LocalDate.now(), user, BigDecimal.valueOf(1000));

        //When
        orderRepository.save(order);
        Long id = order.getId();

        //Then
        assertNotNull(id);

    }

    @Test
    void testOrderRepositoryGetAllOrders() {
        //Given
        Group group = new Group("group");
        Product product = new Product("product", "description", BigDecimal.valueOf(1000), group);
        User user = new User("firstname", "lastname", "email", "address", false);
        User user2 = new User("firstname2", "lastname2", "email2", "address2", false);
        groupRepository.save(group);
        productRepository.save(product);
        userRepository.save(user);
        userRepository.save(user2);

        Order order = new Order(LocalDate.now(), user, BigDecimal.valueOf(1000));
        Order order2 = new Order(LocalDate.now(), user2, BigDecimal.valueOf(1000));

        orderRepository.save(order);
        orderRepository.save(order2);

        //When
        List<Order> all = orderRepository.findAll();
        int size = all.size();

        //Then
        assertNotNull(all);

    }

    @Test
    void testOrderRepositoryGetOrder() {
        //Given
        Group group = new Group("group");
        Product product = new Product("product", "description", BigDecimal.valueOf(1000), group);
        User user = new User("firstname", "lastname", "email", "address", false);
        groupRepository.save(group);
        productRepository.save(product);
        userRepository.save(user);

        Order order = new Order(LocalDate.now(), user, BigDecimal.valueOf(1000));
        Order savedOrder = orderRepository.save(order);

        //When
        Optional<Order> byId = orderRepository.findById(savedOrder.getId());
        Order orderById = byId.get();

        //Then
        assertEquals(1000, orderById.getTotalPrice().intValue());
        assertNotNull(orderById);

    }

    @Test
    void testOrderRepositoryDeleteOrder() {
        //Given
        Group group = new Group("group");
        Product product = new Product("product", "description", BigDecimal.valueOf(1000), group);
        User user = new User("firstname", "lastname", "email", "address", false);
        groupRepository.save(group);
        productRepository.save(product);
        userRepository.save(user);

        Order order = new Order(LocalDate.now(), user, BigDecimal.valueOf(1000));
        orderRepository.save(order);

        //When
        orderRepository.deleteById(order.getId());

        //Then
        assertEquals(0, orderRepository.findAll().size());
        assertTrue(productRepository.existsById(product.getId()));
        assertTrue(userRepository.existsById(user.getId()));

    }

    @Test
    void testOrderRepositoryUpdateOrder() {
        //Given
        Group group = new Group("group");
        Product product = new Product("product", "description", BigDecimal.valueOf(1000), group);
        User user = new User("firstname", "lastname", "email", "address", false);
        groupRepository.save(group);
        productRepository.save(product);
        userRepository.save(user);

        Order order = new Order(LocalDate.now(), user, BigDecimal.valueOf(1000));
        orderRepository.save(order);
        List<Order> allBeforeUpdate = orderRepository.findAll();

        //When
        Optional<Order> updateOrderOptional = orderRepository.findById(order.getId());
        Order updateOrder = updateOrderOptional.get();
        updateOrder.setTotalPrice(BigDecimal.valueOf(5000));
        Order savedUpdatedOrder = orderRepository.save(updateOrder);

        List<Order> allAfterUpdate = orderRepository.findAll();

        //Then
        assertEquals(5000, savedUpdatedOrder.getTotalPrice().intValue());
        assertEquals(allBeforeUpdate.size(), allAfterUpdate.size());

    }

}
