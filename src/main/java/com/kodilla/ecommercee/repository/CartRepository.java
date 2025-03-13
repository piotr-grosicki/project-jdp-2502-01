package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface CartRepository extends CrudRepository<Cart, Integer> {

}
