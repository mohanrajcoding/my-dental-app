package com.auth_service.controller;

import com.auth_service.dto.LoginRequest;
import com.auth_service.dto.LoginResponse;
import com.auth_service.dto.SignupRequest;
import com.auth_service.dto.UserDetails;
import com.auth_service.entity.AppUser;
import com.auth_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final UserService userService;
    @PostMapping("/auth/signup")
    public ResponseEntity<Map<String,String>> signUp(@RequestBody SignupRequest req){
        userService.addUser(req);
        return ResponseEntity.ok(Collections.singletonMap("message","User registered successfully..!"));
    }
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req){
        LoginResponse response = userService.login(req);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/admin/adduser")
    public ResponseEntity<Map<String,String>> addUser(@RequestBody SignupRequest req){
        userService.addUser(req);
        return ResponseEntity.ok(Collections.singletonMap("message","User registered successfully..!"));
    }
    @PutMapping("/admin/modify-user/{id}")
    public ResponseEntity<Map<String,String>> modifyUser(@RequestBody SignupRequest req,@PathVariable Long id){
        userService.modifyUser(req,id);
        return ResponseEntity.ok(Collections.singletonMap("message","User details updated successfully..!"));
    }
    @DeleteMapping("/admin/delete-user/{id}")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(Collections.singletonMap("message","User deleted successfully..!"));
    }

    @GetMapping("/admin/user-details/{id}")
    public ResponseEntity<UserDetails> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/admin/all-users")
    public ResponseEntity<List<UserDetails>> getAllUser(){
        return ResponseEntity.ok(userService.getAllUser());
    }
}
