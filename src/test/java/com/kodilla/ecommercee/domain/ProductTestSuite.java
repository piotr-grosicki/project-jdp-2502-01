package com.kodilla.ecommercee.domain;

import com.kodilla.ecommercee.repository.GroupRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductTestSuite {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GroupRepository groupRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    void shouldFindAllProducts() {
//        GIVEN
        Group group = new Group(null, "Fruits", new ArrayList<>());
        groupRepository.save(group);
        Product product1 = new Product(null, "Apple", "Fresh apple", new BigDecimal("1.99"), group);
        productRepository.save(product1);
//        WHEN
        Long productId = product1.getId();
        Product result = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
//        THEN
        assertNotNull(result);
        assertEquals("Fresh apple", result.getDescription());
    }

    @Test
    void shouldGetProductById() {
//        GIVEN
        Group group = new Group(null, "Fruits", new ArrayList<>());
        groupRepository.save(group);
        Product product1 = new Product(null, "Apple", "Fresh apple", new BigDecimal("1.99"), group);
        productRepository.save(product1);
//        WHEN
        Optional<Product> result = productRepository.findById(product1.getId());
//        THEN
        assertEquals("Apple", result.get().getName());
    }

    @Test
    @Transactional
    void shouldCreateProduct() {
//        GIVEN
        Group group = new Group(null, "Fruits", new ArrayList<>());
        groupRepository.save(group);
        Product product = new Product();
        product.setName("Orange");
        product.setDescription("Fresh orange");
        product.setPrice(new BigDecimal("3.99"));
        product.setGroup(group);
        productRepository.save(product);
//      WHEN
        Long productId = product.getId();
        Product result = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
//      THEN
        assertNotNull(result);
        assertEquals("Orange", result.getName());
    }

    @Test
    void shouldUpdateProduct() {
//        GIVEN
        Group group = new Group(null, "Fruits", new ArrayList<>());
        groupRepository.save(group);
        Product product1 = new Product(null, "Apple", "Fresh apple", new BigDecimal("1.99"), group);
        productRepository.save(product1);
//        WHEN
        product1.setName("Green Apple");
        productRepository.save(product1);
        Optional<Product> updatedProduct = productRepository.findById(product1.getId());
//        THEN
        assertTrue(updatedProduct.isPresent());
        assertEquals("Green Apple", updatedProduct.get().getName());
    }

    @Test
    @Transactional
    void shouldDeleteProduct() {
        // GIVEN
        Group group = new Group(null, "Fruits", new ArrayList<>());
        groupRepository.save(group);
        Product product1 = new Product(null, "Apple", "Fresh apple", new BigDecimal("1.99"), group);
        productRepository.save(product1);

        // WHEN
        productRepository.deleteById(product1.getId());


        // Then
        assertFalse(productRepository.findById(product1.getId()).isPresent());

    }

    @Test
    public void shouldSaveProductsInGroup() {
        // GIVEN
        Group group = new Group(null, "Fruits", new ArrayList<>());
        groupRepository.save(group);

        Product product1 = new Product(null, "Apple", "Fresh apple", new BigDecimal("1.99"), group);
        productRepository.save(product1);

        // WHEN
        Group savedGroup = groupRepository.findById(group.getId())
                .orElseThrow(() -> new RuntimeException("Group not found"));

        List<Product> productsInGroup = savedGroup.getProducts();

        // THEN
        assertNotNull(productsInGroup, "Lista produktów nie powinna być null!");
        assertEquals(1, productsInGroup.size(), "Lista produktów powinna zawierać 1 element.");
        assertEquals("Apple", productsInGroup.get(0).getName(), "Nazwa produktu powinna być 'Apple'");
    }
}