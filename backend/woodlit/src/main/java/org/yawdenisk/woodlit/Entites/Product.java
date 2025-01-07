package org.yawdenisk.woodlit.Entites;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false, length = 1024)
    private String description;
    @Column(nullable = false)
    private float price;
    @Column(nullable = false, length = 1024)
    private String features;
}
