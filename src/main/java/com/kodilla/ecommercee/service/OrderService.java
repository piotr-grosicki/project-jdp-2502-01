package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Order saveOrder(final Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(final Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void deleteOrder(final Long id) {
        orderRepository.deleteById(id);
    }

    public Order updateOrder(final OrderDto orderDto) {
        Order order1 = orderRepository.findById(orderDto.getId()).orElse(null);
        order1.setOrderDate(orderDto.getOrderDate());
        order1.setTotalPrice(orderDto.getTotalPrice());
        order1.setUser(userRepository.findById(orderDto.getUserId()).orElse(null));

        return orderRepository.save(order1);

    }

}
