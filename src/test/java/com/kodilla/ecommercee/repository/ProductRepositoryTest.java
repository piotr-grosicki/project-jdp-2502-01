package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductRepositoryTest {

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
        Product product1 = new Product(null, "Apple", "Fresh apple", new BigDecimal("1.99"), null);
        productRepository.save(product1);
//        WHEN
        Long productId = product1.getId();
        List<Product> result = productRepository.findAll();
//        THEN
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void shouldGetProductById() {
//        GIVEN
        Group group = new Group(null, "Fruits", new ArrayList<>());
        groupRepository.save(group);
        Product product1 = new Product(null, "Apple", "Fresh apple", new BigDecimal("1.99"), null);
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
        Product product = new Product();
        product.setName("Orange");
        product.setDescription("Fresh orange");
        product.setPrice(new BigDecimal("3.99"));
        product.setGroup(null);
//      WHEN
        Product result = productRepository.save(product);
//      THEN
        assertNotNull(result);
        assertEquals("Orange", result.getName());
        assertEquals(1, productRepository.findAll().size());
    }

    @Test
    void shouldUpdateProduct() {
//        GIVEN
        Group group = new Group(null, "Fruits", new ArrayList<>());
        groupRepository.save(group);
        Product product1 = new Product(null, "Apple", "Fresh apple", new BigDecimal("1.99"), null);
        productRepository.save(product1);
//        WHEN
        product1.setName("Green Apple");
        productRepository.save(product1);
        int allProducts = productRepository.findAll().size();
        Optional<Product> updatedProduct = productRepository.findById(product1.getId());
//        THEN
        assertTrue(updatedProduct.isPresent());
        assertEquals(1,allProducts);
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
        List<Product> productsAfterDelete = productRepository.findAll();

        // Then
        Assertions.assertEquals(0, productsAfterDelete.size(), "Liczba produktów w bazie powinna wynosić 0");

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