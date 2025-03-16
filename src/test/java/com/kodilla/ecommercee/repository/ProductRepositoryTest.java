package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.Product;
import jakarta.transaction.Transactional;
import org.assertj.core.api.AbstractIterableAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.math.BigDecimal;
import java.util.ArrayList;
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
        Long groupId = group.getId();

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
        Optional<Product> result = productRepository.findById(product1.getId());
        assertTrue(result.isPresent(), "Product should be found in the database");
        assertEquals("Apple", result.get().getName());
    }

    @Test
    void shouldCreateProduct() {
        product3 = new Product();
        product3.setName("Orange");
        product3.setDescription("Fresh orange");
        product3.setPrice(new BigDecimal("3.99"));
        product3.setGroup(group);
        Long groupId = group.getId();
        Long productsInGroup = groupRepository.findById(groupId).get().getProducts().stream().count();

        Product result = productRepository.save(product3);
        group.getProducts().add(product3);
        groupRepository.save(group);

        assertNotNull(result);
        assertEquals("Orange", result.getName());
        assertEquals(3, productRepository.findAll().size());
        assertEquals(3, productsInGroup);
    }

    @Test
    void shouldUpdateProduct() {
        product1.setName("Green Apple");
        productRepository.save(product1);
        Optional<Product> updatedProduct = productRepository.findById(product1.getId());
        assertTrue(updatedProduct.isPresent());
        assertEquals("Green Apple", updatedProduct.get().getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void shouldDeleteProduct() {
        // GIVEN
        productRepository.deleteById(product1.getId());

        // Usunięcie produktu z listy w grupie
        group.removeProduct(product1);
        groupRepository.save(group);

        // WHEN
        Group savedGroup = groupRepository.findByIdWithProducts(group.getId())
                .orElseThrow(() -> new RuntimeException("Grupa nie została znaleziona"));

        // THEN
        assertEquals(1, productRepository.findAll().size(), "Liczba produktów w bazie powinna wynosić 1");
        assertEquals(1, savedGroup.getProducts().size(), "Grupa powinna zawierać 1 produkt");
    }

    @Test
    @Transactional
    public void isTestProductsInGroup() {

        Group savedGroup = groupRepository.findByIdWithProducts(group.getId())
                .orElseThrow(() -> new RuntimeException("Grupa nie została znaleziona"));

        assertNotNull(savedGroup.getProducts(), "Lista produktów nie powinna być null!");
        assertEquals(2, savedGroup.getProducts().size(), "Lista produktów powinna zawierać 2 elementy.");
    }
}
