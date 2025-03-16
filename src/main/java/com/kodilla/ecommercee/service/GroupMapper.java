package com.kodilla.ecommercee.service;

import com.kodilla.ecommercee.domain.Group;
import com.kodilla.ecommercee.domain.GroupDto;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupMapper {

    private final ProductRepository productRepository;

    public GroupMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public GroupDto mapToGroupDto(Group group) {
        return new GroupDto(
                group.getId(),
                group.getName(),
                group.getProducts().stream()
                        .map(Product::getId)
                        .collect(Collectors.toList())
        );
    }


    public Group mapToGroup(GroupDto groupDto) {
        List<Product> products = groupDto.getProductsIds().stream()
                .map(id -> productRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("BookCopy with ID " + id + " not found")))
                .collect(Collectors.toList());

        return new Group(
                groupDto.getId(),
                groupDto.getName(),
                products
        );

    }


}
