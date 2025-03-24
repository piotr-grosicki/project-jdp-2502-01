package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public void removeProduct(Long id) {
        if (id != null) {
            repository.deleteById(id);
        }
    }

    public Product updateProduct(Product product) {
        return repository.save(product);
    }

}
