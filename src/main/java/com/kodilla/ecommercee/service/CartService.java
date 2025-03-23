package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.CartRepository;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Cart createCart(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getCart() != null) {
            return user.getCart();
        }
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProducts(new ArrayList<>());
        return cartRepository.save(cart);
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public Cart addProduct(Long cartId, Long productId) {
        Cart cart = getCart(cartId);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProducts().add(product);
        return cartRepository.save(cart);
    }

    public Cart removeProduct(Long cartId, Long productId) {
        Cart cart = getCart(cartId);
        productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        cart.getProducts().removeIf(product -> product.getId().equals(productId));
        return cartRepository.save(cart);
    }

    public Order cartCheckout(long cartId) {
        Cart cart = getCart(cartId);
        if (cart.getProducts().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }
        Order order = new Order();
        order.setId(cart.getId());
        order.setOrderDate(LocalDate.now());
        order.setUser(cart.getUser());
        order.setTotalPrice(cart.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
        order.setProducts(new ArrayList<>(cart.getProducts()));
        Order savedOrder = orderRepository.save(order);
        cart.getProducts().clear();
        cartRepository.save(cart);
        return savedOrder;
    }
}