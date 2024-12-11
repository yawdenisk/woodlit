package org.yawdenisk.woodlit.Entites;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.Optional;
import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String name;
    private String last_name;
    private String email;
    private String password;
    private String country;
    private String city;
    private String post_index;
    private String address;
    private String roles;
}
