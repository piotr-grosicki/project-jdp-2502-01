package com.kodilla.ecommercee.domain;


import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private long id;
    private long userId;
    private List<Long> productIdList;
}
