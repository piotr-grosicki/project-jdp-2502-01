package com.kodilla.ecommercee;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class TestData implements CommandLineRunner {

    private final GroupRepository groupRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Override
    public void run(String... args) throws Exception {

        Group group1 = new Group(1L, "Electronics", new ArrayList<>());
        Group group2 = new Group(2L, "Fruits", new ArrayList<>());
        groupRepository.save(group1);
        groupRepository.save(group2);

        Product product1 = new Product(1L, "LED TV", "Very big LED TV", new BigDecimal("6999"), group1);
        Product product2 = new Product(2L, "Soundbar", "Very laud soundbar", new BigDecimal("3999"), group1);
        Product product3 = new Product(3L, "Apple", "red apple", new BigDecimal("2"), group2);
        Product product4 = new Product(4L, "Banana", "yellow banana", new BigDecimal("5"), group2);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);

        User user1 = new User(1L, "John", "Smith","mail@mail.com", "address", false, null, null,  new ArrayList<>(),null);
        User user2 = new User(2L, "Jan", "Kowalski","mail1@mail.com", "address", false, null, null,  new ArrayList<>(),null);
        userRepository.save(user1);
        userRepository.save(user2);

        System.out.println("Załadowano dane testowe");
    }
}
