package com.kodilla.ecommercee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "PRODUCT_GROUPS") // ✅ Lepsza nazwa dla Hibernate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", unique = true)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // ✅ Zapobiega nieskończonej rekursji JSON
    private List<Products> products;
}