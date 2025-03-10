package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.ProductDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = List.of(
                new ProductDto(1L, "Earbuds", "Highest tech new NC earbuds", 8.99),
                new ProductDto(2L, "T-shirt", "Regular plain t-shirt", 20.99),
                new ProductDto(3L, "History Book part 1", "101 of ancients", 5.56),
                new ProductDto(4L, "Jack Daniels 0.7", "0.7 of good ol' Jack", 4.23)
        );
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(Long id) {
        ProductDto product = new ProductDto(1L, "Test product", "Test description", 10.00);
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public void addProduct() {

    }

    @DeleteMapping(value = "/{productId}")
    public void deleteProduct() {

    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<ProductDto> updateProductName() {
        ProductDto productUpdatedName = new ProductDto(1L, "Test product updated", "Test description", 10.00);
        return ResponseEntity.ok(productUpdatedName);
    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<ProductDto> updateProductDescription() {
        ProductDto productUpdatedDescription = new ProductDto(1L, "Test product", "Test description updated", 10.00);
        return ResponseEntity.ok(productUpdatedDescription);
    }

    @PutMapping(value = "/{productId}")
    public ResponseEntity<ProductDto> updateProductPrice() {
        ProductDto productUpdatedPrice = new ProductDto(1L, "Test product", "Test description", 12.50);
        return ResponseEntity.ok(productUpdatedPrice);

    }
}
