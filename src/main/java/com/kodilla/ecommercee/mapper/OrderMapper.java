package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.Order;
import com.kodilla.ecommercee.domain.OrderDto;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.repository.OrderRepository;
import com.kodilla.ecommercee.repository.ProductRepository;
import com.kodilla.ecommercee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderMapper {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    public OrderMapper(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }


    public Order mapToOrder(final OrderDto orderDto) {
        Long userId = orderDto.getUserId();
        Optional<User> userById = userRepository.findById(userId);
        User user = userById.get();
        List<Product> productsList = orderDto.getProductsIds().stream()
                .map(l -> productRepository.findById(l).get())
                .collect(Collectors.toList());


        return new Order(orderDto.getId(),
                orderDto.getOrderDate(),
                user,
                orderDto.getTotalPrice(),
                productsList);
    }

    public OrderDto mapToOrderDto(final Order order) {
        OrderDto orderDto = new OrderDto(order.getId(),
                order.getOrderDate(),
                order.getUser().getId(),
                order.getTotalPrice(),
                order.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toList()));
        return orderDto;
    }

    public List<OrderDto> mapToOrderDtoList(final List<Order> orders) {
        List<OrderDto> orderDtoList = orders.stream()
                .map(this::mapToOrderDto)
                .collect(Collectors.toList());
        return orderDtoList;
    }

}
