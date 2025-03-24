package com.kodilla.ecommercee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private boolean blocked;
    private String token;
    private LocalDateTime expiresAt;
    private List<Order> orders;
    private Cart cart;
}
