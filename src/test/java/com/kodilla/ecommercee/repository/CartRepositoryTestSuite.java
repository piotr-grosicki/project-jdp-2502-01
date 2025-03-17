package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CartRepositoryTestSuite {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GroupRepository groupRepository;

    private Cart cart1;
    private Product product1;
    private Product product2;
    private Group group1;

    @BeforeEach
    void setUp() {
        group1 = new Group();
        group1.setName("Drinks");
        groupRepository.save(group1);

        product1 = new Product();
        product1.setName("Soda");
        product1.setDescription("Bubbly drink");
        product1.setPrice(new BigDecimal("0.99"));
        product1.setGroup(group1);
        productRepository.save(product1);

        product2 = new Product();
        product2.setName("Water");
        product2.setDescription("Fresh water");
        product2.setPrice(new BigDecimal("0.39"));
        product2.setGroup(group1);
        productRepository.save(product2);

        Product managedProduct1 = productRepository.findById(product1.getId()).orElseThrow();
        Product managedProduct2 = productRepository.findById(product2.getId()).orElseThrow();

        cart1 = new Cart();
        cart1.setProducts(List.of(managedProduct1, managedProduct2));
        cartRepository.save(cart1);
    }

    @AfterEach
    void tearDown() {
        cartRepository.deleteAll();
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    void testCreateCart() {
        //GIVEN
        Cart cart2 = new Cart();

        //WHEN
        cartRepository.save(cart2);
        List<Cart> savedCarts = cartRepository.findAll();
        int cartsCounted = savedCarts.size();

        //THEN
        assertEquals(2, cartsCounted);
    }

    @Test
    void testUpdateCartAddProduct() {
        //GIVEN
        Product product3 = new Product();
        product3.setName("Juice");
        product3.setDescription("Sweet juice");
        product3.setPrice(new BigDecimal("0.59"));
        product3.setGroup(group1);
        productRepository.save(product3);
        Product managedProduct3 = productRepository.findById(product3.getId()).orElseThrow();

        //WHEN
        cart1.getProducts().add(managedProduct3);
        cartRepository.save(cart1);
        Optional<Cart> savedCart = cartRepository.findById(cart1.getId());

        //THEN
        assertTrue(savedCart.isPresent());
        assertEquals(3, cartRepository.findAll().size());
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
    void testDeleteCart() {
        //GIVEN
        //WHEN
        cartRepository.delete(cart1);
        //THEN
        assertEquals(0, cartRepository.findAll().size());
    }

    @Test
    void testFindCartById() {
        //GIVEN
        Cart cart2 = new Cart();

        //WHEN
        cartRepository.save(cart2);
        Optional<Cart> savedCart = cartRepository.findById(cart2.getId());
        Optional<Cart> savedCart1 = cartRepository.findById(cart1.getId());

        //THEN
        assertTrue(savedCart.isPresent());
        assertTrue(savedCart1.isPresent());
        assertEquals(2, cartRepository.findAll().size());
        assertEquals(cart2.getId(), savedCart.get().getId());
        assertEquals(cart1.getId(), savedCart1.get().getId());
    }
}
