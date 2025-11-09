package com.auth_service.service;

import com.auth_service.dto.LoginRequest;
import com.auth_service.dto.LoginResponse;
import com.auth_service.dto.SignupRequest;
import com.auth_service.dto.UserDetails;

import java.util.List;

public interface UserService {
    void addUser(SignupRequest request);

    LoginResponse login(LoginRequest req);

    void modifyUser(SignupRequest req, Long id);

    void deleteUser(Long id);

    UserDetails getUserById(Long id);

    List<UserDetails> getAllUser();
}
