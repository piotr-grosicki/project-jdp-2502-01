package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT_GROUPS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {

    public Group(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GROUP_ID")
    private Long id;

    @Column(name = "GROUP_NAME", nullable = false, length = 100)
    private String name;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Group{id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", products=[");

        for (Product product : products) {
            sb.append("{id=").append(product.getId())
                    .append(", name='").append(product.getName()).append("'}, ");
        }

        if (!products.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("]}");
        return sb.toString();
    }
}