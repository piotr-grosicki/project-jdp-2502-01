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
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService service;
    private ProductMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = service.getAllProducts();
        return ResponseEntity.ok(mapper.mapToProductDtoList(products));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product product = service.getProductById(id);
        return ResponseEntity.ok(mapper.mapToProductDto(product));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        Product product = mapper.mapToProduct(productDto);
        Product addedProduct = service.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.mapToProductDto(addedProduct));
    }

    @DeleteMapping(value = "/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        service.removeProduct(productId);
    }

    @PutMapping(value = "/{productId}/name")
    public ResponseEntity<ProductDto> updateProductName(@RequestBody ProductDto productDto) {
        Product product = mapper.mapToProduct(productDto);
        product.setName(productDto.getName());
        return ResponseEntity.ok(mapper.mapToProductDto(product));
    }

    @PutMapping(value = "/{productId}/description")
    public ResponseEntity<ProductDto> updateProductDescription(@RequestBody ProductDto productDto) {
        Product product = mapper.mapToProduct(productDto);
        product.setDescription(productDto.getDescription());
        return ResponseEntity.ok(mapper.mapToProductDto(product));
    }

    @PutMapping(value = "/{productId}/price")
    public ResponseEntity<ProductDto> updateProductPrice(@RequestBody ProductDto productDto) {
        Product product = mapper.mapToProduct(productDto);
        product.setPrice(productDto.getPrice());
        return ResponseEntity.ok(mapper.mapToProductDto(product));
    }
}
