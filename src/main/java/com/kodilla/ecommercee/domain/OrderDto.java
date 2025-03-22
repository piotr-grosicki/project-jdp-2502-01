package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private LocalDate orderDate;
    private Long userId;
    private BigDecimal totalPrice;
    private List<Long> productsIds;

}
