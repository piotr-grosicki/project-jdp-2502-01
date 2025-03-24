package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    public User(String firstname, String lastname, String email, String address, boolean blocked) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.blocked = blocked;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false)
    private Long id;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstname;

    @Column(name = "LASTNAME", nullable = false)
    private String lastname;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "BLOCKED", nullable = false)
    private boolean blocked;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Order> orders;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Cart cart;

}
