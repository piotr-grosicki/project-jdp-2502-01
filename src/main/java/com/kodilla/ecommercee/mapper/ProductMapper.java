package com.kodilla.ecommercee.mapper;

import com.kodilla.ecommercee.domain.ProductDto;
import com.kodilla.ecommercee.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public Product mapToProduct(final ProductDto productDto) {
        return new Product(
                productDto.getId(),
                null,
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                null
                );
    }

    public ProductDto mapToProductDto(final Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
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
