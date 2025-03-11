package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
public class CartController {

    @PostMapping("/{userId}")
    public CartDto createCart(@PathVariable long userId) {
        return new CartDto(1L, userId, new ArrayList<>());
    }

    @GetMapping("/{cartId}")
    public CartDto getCart(@PathVariable long cartId) {
        return new CartDto(cartId,2L, Arrays.asList(1L, 2L, 3L));
    }

    @PostMapping("/{cartId}/products/{productId}")
    public CartDto addProductToCart(@PathVariable long cartId, @PathVariable long productId) {
        return new CartDto(cartId, 2L, List.of(productId));
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public String removeProductFromCart(@PathVariable long cartId, @PathVariable long productId) {
        return "The product " + productId + " has been removed from the cart.";
    }

    @PostMapping("{cartId}/checkout")
    public String checkoutCart(@PathVariable long cartId) {
        return "A new order has been created.";
    }

}
