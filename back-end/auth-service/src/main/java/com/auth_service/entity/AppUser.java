package com.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;


@Entity
@Table(name = "app_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String name;
    private String role;
    private Integer age;
    private String address;
    private Instant createdAt;
    private Instant updatedAt;
    private String password;
    @PrePersist
    private void prePersist(){
        createdAt = Instant.now();
        updatedAt = createdAt;
    }
    @PostPersist
    private void preUpdate(){
        updatedAt = Instant.now();
    }

}
