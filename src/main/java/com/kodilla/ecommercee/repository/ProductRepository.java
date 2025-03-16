package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    List<Product> findAll();

    Product deleteById(Product product);

    @Override
    Optional<Product> findById(Long id);

    void deleteById(int i);
}
