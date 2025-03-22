package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
}

