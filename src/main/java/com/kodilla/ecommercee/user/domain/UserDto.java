package com.kodilla.ecommercee.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private LocalDate dateOfExpiry;
//    private CartDto cart;
    private List<Long> ordersId;

}
