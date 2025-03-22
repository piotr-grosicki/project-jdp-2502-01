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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private GroupRepository groupRepository;

    private Product product1;
    private Product product2;
    private Product product3;
    private Group group;

    @BeforeEach
    void setUp() {
        // Tworzenie grupy
        group = new Group();
        group.setName("Fruits");
        group = groupRepository.save(group);

        // Tworzenie produktów i przypisanie grupy
        product1 = new Product();
        product1.setName("Apple");
        product1.setDescription("Fresh apple");
        product1.setPrice(new BigDecimal("1.99"));
        product1.setGroup(group);
        product1 = productRepository.save(product1);

        product2 = new Product();
        product2.setName("Banana");
        product2.setDescription("Fresh banana");
        product2.setPrice(new BigDecimal("2.99"));
        product2.setGroup(group);
        product2 = productRepository.save(product2);

        group.setProducts(new ArrayList<>(List.of(product1, product2)));
        groupRepository.save(group);
    }
    @AfterEach
    void tearDown() {
        productRepository.deleteAll();
        groupRepository.deleteAll();
    }

    @Test
    void shouldFindAllProducts() {
        List<Product> result = productRepository.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void shouldGetProductById() {
//        GIVEN
//        WHEN
        Optional<Product> result = productRepository.findById(product1.getId());
//        THEN
        assertTrue(result.isPresent(), "Product should be found in the database");
        assertEquals("Apple", result.get().getName());
    }

    @Test
    @Transactional
    void shouldCreateProduct() {
//        GIVEN
        product3 = new Product();
        product3.setName("Orange");
        product3.setDescription("Fresh orange");
        product3.setPrice(new BigDecimal("3.99"));
        product3.setGroup(group);
        Long groupId = group.getId();
//      WHEN
        Product result = productRepository.save(product3);
        group.getProducts().add(product3);
        Long productsInGroup = groupRepository.findById(groupId).get().getProducts().stream().count();
        groupRepository.save(group);
//      THEN
        assertNotNull(result);
        assertEquals("Orange", result.getName());
        assertEquals(3, productRepository.findAll().size());
        assertEquals(3, productsInGroup);
    }

    @Test
    void shouldUpdateProduct() {
//        GIVEN
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
        List<Product> productsBefore = productRepository.findAll();
        System.out.println("Before delete: " + productsBefore);


        group.getProducts().removeIf(p -> p.getId().equals(product1.getId()));
        groupRepository.save(group);

        // WHEN
        productRepository.deleteById(product1.getId());

        List<Product> productsAfterDelete = productRepository.findAll();
        System.out.println("After delete: " + productsAfterDelete);

        // Then
        Assertions.assertEquals(1, productsAfterDelete.size(), "Liczba produktów w bazie powinna wynosić 1");
        Assertions.assertFalse(productsAfterDelete.stream().anyMatch(p -> p.getId().equals(product1.getId())));
    }

    @Test
    public void isTestProductsInGroup() {
//      GIVEN
//      WHEN
        Group savedGroup = groupRepository.findByIdWithProducts(group.getId())
                .orElseThrow(() -> new RuntimeException("Grupa nie została znaleziona"));
//      THEN
        assertNotNull(savedGroup.getProducts(), "Lista produktów nie powinna być null!");
        assertEquals(2, savedGroup.getProducts().size(), "Lista produktów powinna zawierać 2 elementy.");
    }
}