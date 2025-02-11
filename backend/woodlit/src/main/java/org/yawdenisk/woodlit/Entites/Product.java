package org.yawdenisk.woodlit.Entites;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @UuidGenerator
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false, length = 1024)
    private String description;
    @Column(nullable = false)
    private float price;
    @Column(nullable = false)
    private float instalationPrice;
    @Column(nullable = false, length = 1024)
    private String features;
}
