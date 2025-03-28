package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class CartMapper {

    public CartDto mapToCartDto(final Cart cart) {
        return new CartDto(cart.getId(), cart.getUser().getId(), cart.getProducts().stream().map(Product::getId).toList());
    }

}
