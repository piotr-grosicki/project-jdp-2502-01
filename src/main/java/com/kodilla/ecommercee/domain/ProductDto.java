package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDto {
    private final Long productId;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private Long groupId;
}
