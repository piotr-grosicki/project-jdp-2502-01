package com.kodilla.ecommercee.product;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductControllerTestSuite {

    private ProductRepository repository;
    private ProductService service;

    @Test
    public void FirstTest() {
        //Given
        Product product1 = new Product(1L, null, "Earbuds", "Tech earbuds", new BigDecimal("8.99"));
        Product product2 = new Product(2L, null, "T-shirt", "Plain t-shirt", new BigDecimal("20.99"));
        List<Product> mockProducts = List.of(product1, product2);

        // When
        List<Product> result = service.getAllProducts();

        //Then
        assertEquals(2, result.size());
        assertEquals("Earbuds", result.get(0).getName());

    }
}
