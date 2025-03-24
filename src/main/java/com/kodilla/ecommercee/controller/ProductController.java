package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.mapper.ProductMapper;
import com.kodilla.ecommercee.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;



    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = service.getAllProducts();
        if (products == null) {
            return ResponseEntity.ok(List.of());
        }
        return ResponseEntity.ok(mapper.mapToProductDtoList(products));
    }

    @GetMapping(value = "/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) {
        Product product = service.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(mapper.mapToProductDto(product));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        Product product = mapper.mapToProduct(productDto);
        Product addedProduct = service.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToProductDto(addedProduct));
    }

    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        Product product = service.getProductById(productId);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        service.removeProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}/name")
    public ResponseEntity<ProductDto> updateProductName(
            @PathVariable Long productId, @RequestBody ProductDto productDto) {
        Product existingProduct = service.getProductById(productId);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }
        existingProduct.setName(productDto.getName());
        Product updatedProduct = service.updateProduct(existingProduct);
        return ResponseEntity.ok(mapper.mapToProductDto(updatedProduct));
    }

    @PutMapping("/{productId}/description")
    public ResponseEntity<ProductDto> updateProductDescription(
            @PathVariable Long productId, @RequestBody ProductDto productDto) {
        Product existingProduct = service.getProductById(productId);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }
        existingProduct.setDescription(productDto.getDescription());
        Product updatedProduct = service.updateProduct(existingProduct);
        return ResponseEntity.ok(mapper.mapToProductDto(updatedProduct));
    }

    @PutMapping("/{productId}/price")
    public ResponseEntity<ProductDto> updateProductPrice(
            @PathVariable Long productId, @RequestBody ProductDto productDto) {
        Product existingProduct = service.getProductById(productId);
        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }
        existingProduct.setPrice(productDto.getPrice());
        Product updatedProduct = service.updateProduct(existingProduct);
        return ResponseEntity.ok(mapper.mapToProductDto(updatedProduct));
    }

}
