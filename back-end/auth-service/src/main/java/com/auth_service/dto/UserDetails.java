package com.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    private Long id;
    private String email;
    private String name;
    private String role;
    private Integer age;
    private String address;
}
