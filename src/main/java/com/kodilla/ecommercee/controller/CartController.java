package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartDto;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.mapper.CartMapper;
import com.kodilla.ecommercee.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartMapper cartMapper;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> createCart(@PathVariable long userId) {
        Cart cart = cartService.createCart(userId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable long cartId) {
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @PostMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartDto> addProductToCart(@PathVariable long cartId, @PathVariable long productId) {
        Cart cart = cartService.addProduct(cartId, productId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartDto> removeProductFromCart(@PathVariable long cartId, @PathVariable long productId) {
        Cart cart = cartService.removeProduct(cartId, productId);
        return ResponseEntity.ok(cartMapper.mapToCartDto(cart));
    }

    @PostMapping("{cartId}/checkout")
    public ResponseEntity<OrderDto> checkoutCart(@PathVariable long cartId) {
        Order order = cartService.cartCheckout(cartId);
        return ResponseEntity.ok(new OrderDto(order.getId(), order.getOrderDate(), order.getUser().getId(), order.getTotalPrice(), order.getProducts()));
    }

}
