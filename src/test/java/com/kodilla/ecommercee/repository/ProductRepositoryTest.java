package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Product;
import org.assertj.core.api.AbstractIterableAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    private Product product1;
    private Product product2;
    private Product product3;
    private Cart cart1;
    private Cart cart2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setProductName("Apple");
        product1 = productRepository.save(product1);

        product2 = new Product();
        product2.setProductName("Banana");
        product2 = productRepository.save(product2);
    }

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
    }

    @Test
    void shouldFindAllProducts() {
        // GIVEN
        // WHEN
        List<Product> result = productRepository.findAll();
        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
    }


    @Test
    void shouldGetProductbyId() {
        // GIVEN
        // WHEN
        Optional<Product> result = productRepository.findById(product1.getId());

        // THEN
        assertTrue(result.isPresent(), "Product should be found in the database");
        assertEquals("Apple", result.get().getProductName());
    }

    @Test
    void shouldCreateProduct() {
//        GIVEN
        product3 = new Product();
        product3.setProductName("Orange");
//        WHEN
        Product result = productRepository.save(product3);
//        THEN
        assertNotNull(result);
        assertEquals("Orange", result.getProductName());
    }

    @Test
    void shouldUpdateCreateProduct() {
//        GIVEN
        Product newProduct = new Product();
        newProduct.setProductName("Pineapple");
//        WHEN
        Product result = productRepository.save(newProduct);
//        THEN
        assertNotNull(result);
        assertEquals("Pineapple", result.getProductName());
        assertEquals(3, productRepository.findAll().size());
    }

    @Test
    void shouldRemoveCreateProduct() {
//        GIVEN
//        WHEN
        productRepository.delete(product1);
        productRepository.delete(product2);
//        THEN
        Optional<Product> deletedProduct1 = productRepository.findById(product1.getId());
        Optional<Product> deletedProduct2 = productRepository.findById(product2.getId());

        assertFalse(deletedProduct1.isPresent(), "Product 1 should be removed from database");
        assertFalse(deletedProduct2.isPresent(), "Product 2 should be removed from database");

        List<Product> allProducts = productRepository.findAll();
        assertEquals(0, allProducts.size(), "Database should be empty after deletion");

    }


}