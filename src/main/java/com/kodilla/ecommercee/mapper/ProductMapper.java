package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.domain.Product;
import com.kodilla.ecommercee.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final GroupRepository groupRepository;

    public Product mapToProduct(final ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setGroup(groupRepository.findById(productDto.getGroupId()).orElse(null));
        return product;

    }

    public ProductDto mapToProductDto(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getGroup() != null ? product.getGroup().getId() : null
        );
    }

    public List<ProductDto> mapToProductDtoList(final List<Product> productsList) {
        return productsList.stream()
                .map(this::mapToProductDto)
                .toList();
    }

    public List<Product> mapToProductsList(final List<ProductDto> productsDtoList) {
        return productsDtoList.stream()
                .map(this::mapToProduct)
                .toList();
    }
}
