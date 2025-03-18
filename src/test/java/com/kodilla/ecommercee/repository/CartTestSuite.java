package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CartTestSuite {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    private Cart cart1;
    private Product product1;
    private Product product2;
    private Group group1;

    @BeforeEach
    void setUp() {
        group1 = new Group(1L, "Drinks", new ArrayList<>());
        groupRepository.save(group1);

        product1 = new Product(1L,"Soda", "Bubbly Drink", new BigDecimal("0.99"), group1);
        productRepository.save(product1);

        product2 = new Product(2L,"Water", "Fresh Water", new BigDecimal("0.39"), group1);
        productRepository.save(product2);

        User user1 = new User(null, "Mike", "Wazowski", "mail@mail.com", "Warsaw, Glowna 1 street", false, "1233", LocalDateTime.now(), new ArrayList<>(), null);

        Product managedProduct1 = productRepository.findById(product1.getId()).orElseThrow();
        Product managedProduct2 = productRepository.findById(product2.getId()).orElseThrow();

        cart1 = new Cart(null, user1, new ArrayList<>());
        cart1.getProducts().add(managedProduct1);
        cart1.getProducts().add(managedProduct2);
        user1.setCart(cart1);

        cart1 = cartRepository.save(cart1);
        user1 = userRepository.save(user1);
    }

    @AfterEach
    void tearDown() {
        cartRepository.deleteAll();
        productRepository.deleteAll();
        groupRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testCreateEmptyCart() {
        //GIVEN
        User userWithEmptyCart = new User(null, "John", "Doe", "<EMAIL>", "Warsaw, Glowna 2 street", false, "1234", LocalDateTime.now(), new ArrayList<>(), null);
        Cart emptyCart = new Cart(null, userWithEmptyCart, new ArrayList<>());
        userWithEmptyCart.setCart(emptyCart);

        //WHEN
        Cart savedCart = cartRepository.save(emptyCart);
        userWithEmptyCart = userRepository.save(userWithEmptyCart);
        Optional<Cart> retrievedCart = cartRepository.findById(savedCart.getId());

        //THEN
        assertTrue(retrievedCart.isPresent());
        assertEquals(0, retrievedCart.get().getProducts().size());
    }

    @Test
    void testCreateCart() {
        //GIVEN
        User user2 = new User(null, "John", "Doe", "<EMAIL>", "Warsaw, Glowna 2 street", false, "1234", LocalDateTime.now(), new ArrayList<>(), null);
        Cart cart2 = new Cart(3L, user2, new ArrayList<>());

        //WHEN
        user2.setCart(cart2);
        cart2 = cartRepository.save(cart2);
        user2 = userRepository.save(user2);
        List<Cart> savedCarts = cartRepository.findAll();
        int cartsCounted = savedCarts.size();

        //THEN
        assertEquals(2, cartsCounted);
    }

    @Test
    void testUpdateCartAddProduct() {
        //GIVEN
        Product product3 = new Product(3L, "Coffee", "Smooth Coffee", new BigDecimal("0.59"), group1);
        productRepository.save(product3);
        Product managedProduct3 = productRepository.findById(product3.getId()).orElseThrow();

        //WHEN
        cart1.getProducts().add(managedProduct3);
        cartRepository.save(cart1);
        Optional<Cart> savedCart = cartRepository.findById((cart1.getId()));

        //THEN
        assertTrue(savedCart.isPresent());
        assertEquals(3, savedCart.get().getProducts().size());
    }

    @Test
    void testUpdateCartRemoveProduct() {
        //GIVEN
        //WHEN
        cart1.getProducts().remove(product1);
        cartRepository.save(cart1);
        Optional<Cart> savedCart = cartRepository.findById(cart1.getId());

        //THEN
        assertTrue(savedCart.isPresent());
        assertEquals(1, cartRepository.findAll().size());
    }

    @Test
    void testRemoveSpecificProduct() {
        //GIVEN
        Product productToRemove = product1;

        //WHEN
        cart1.getProducts().remove(productToRemove);
        cart1 = cartRepository.save(cart1);
        Optional<Cart> savedCart = cartRepository.findById(cart1.getId());

        //THEN
        assertTrue(savedCart.isPresent());
        assertFalse(savedCart.get().getProducts().contains(productToRemove));
    }

    @Test
    void testDeleteCart() {
        //GIVEN
        //WHEN
        cartRepository.delete(cart1);
        //THEN
        assertEquals(0, cartRepository.findAll().size());
    }

    @Test
    void testDeleteCartDoesNotDeleteProducts() {
        //GIVEN
        //WHEN
        cartRepository.delete(cart1);
        List<Product> remainingProducts = productRepository.findAll();

        //THEN
        assertEquals(2, remainingProducts.size());
    }

    @Test
    void testFindCartById() {
        //GIVEN

        //WHEN
        Optional<Cart> savedCart1 = cartRepository.findById(cart1.getId());

        //THEN
        assertTrue(savedCart1.isPresent());
        assertEquals(cart1.getId(), savedCart1.get().getId());
    }

    @Test
    void testFindCartByUser() {
        //GIVEN
        //WHEN
        Optional<Cart> retrievedCart = cartRepository.findById(cart1.getId());

        //THEN
        assertTrue(retrievedCart.isPresent());
        assertNotNull(retrievedCart.get().getUser());
        assertEquals("Mike", retrievedCart.get().getUser().getFirstname());
    }
}
