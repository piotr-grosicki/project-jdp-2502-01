package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class UserTestSuite {

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Cart cart;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstname("Mike");
        user.setLastname("Kowalski");
        user.setEmail("mike.blabla@.com");
        user.setAddress("123 Kwiatowa");
        user.setBlocked(false);
        user.setToken("test-token");
        user.setExpiresAt(LocalDateTime.now().plusDays(1));
        user.setOrders(new ArrayList<>());
        user.setCart(new Cart());

        cart = new Cart();
        cart.setUser(user);
        cart.setProducts(new ArrayList<>());
        user.setCart(cart);
    }

    @Test
    void testSaveUser() {
        // Given
        // User is created in BeforeEach

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertNotNull(savedUser.getId());
        assertEquals("Mike", savedUser.getFirstname());
        assertEquals("Kowalski", savedUser.getLastname());
        assertEquals("mike.blabla@.com", savedUser.getEmail());
        assertEquals("123 Kwiatowa", savedUser.getAddress());
        assertFalse(savedUser.isBlocked());
        assertEquals("test-token", savedUser.getToken());
        assertNotNull(savedUser.getExpiresAt());
    }

    @Test
    void testFindUserById() {
        // Given
        // User is created in BeforeEach
        User savedUser = userRepository.save(user);

        // When
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        //Then
        assertNotNull(foundUser);
        assertEquals("Mike", foundUser.getFirstname());
        assertEquals(savedUser.getLastname(), foundUser.getLastname());
        assertEquals(savedUser.getId(), foundUser.getId());
    }

    @Test
    void testDeleteUser() {
        // Given
        // User is created in BeforeEach
        User savedUser = userRepository.save(user);

        // When
        userRepository.deleteById(savedUser.getId());

        // Then
        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }

    @Test
    void testGetAllUsers() {
        // Given
        // User is created in BeforeEach
        User savedUser = userRepository.save(user);

        User user2 = new User();
        user2.setFirstname("Adam");
        user2.setLastname("Nowak");
        user2.setEmail("shrek.blabla@.com");
        user2.setAddress("9 Kwiatowa");
        user2.setBlocked(false);
        user2.setToken("test-token2");
        user2.setExpiresAt(LocalDateTime.now().plusDays(1));
        user2.setOrders(new ArrayList<>());
        user2.setCart(new Cart());

        Cart cart2 = new Cart();
        cart2.setUser(user2);
        cart2.setProducts(new ArrayList<>());
        user2.setCart(cart2);

        userRepository.save(user2);

        //When
        List<User> users = userRepository.findAll();

        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("Mike", users.get(0).getFirstname());
        assertEquals("Nowak", users.get(1).getLastname());
        assertEquals("test-token2", users.get(1).getToken());
    }

}
