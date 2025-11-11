package com.auth_service.service;

import com.auth_service.dto.LoginRequest;
import com.auth_service.dto.LoginResponse;
import com.auth_service.dto.SignupRequest;
import com.auth_service.dto.UserDetails;
import com.auth_service.entity.AppUser;
import com.auth_service.exception.BusinessException;
import com.auth_service.logging.LogManager;
import com.auth_service.repository.UserRepository;
import com.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final LogManager logManager;
    @Override
    public void addUser(SignupRequest request) {
        logManager.logDebug("UserServiceImpl:: signUp");
        AppUser user = new AppUser();
        user.setAddress(request.getAddress());
        user.setAge(request.getAge());
        user.setName(request.getName());
        String role = request.getRole();
        user.setRole(role==null || role.isEmpty() ||
                role.equalsIgnoreCase("USER")?"USER":"ADMIN");
     ///   if(request.getRole().isEmpty() || request.getRole().equalsIgnoreCase("USER")){
     ///user.setRole("USER");
     ///}else {
     ///user.setRole("ADMIN");
     ///}
     user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        logManager.logDebug("UserServiceImpl:: login");
        AppUser user = userRepository.findByEmail(req.getEmail());
        if (user==null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new BusinessException("Invalid email or password");
        }
        return new LoginResponse(jwtUtil.generateAccessToken(user),user.getRole());
    }

    @Override
    public void modifyUser(SignupRequest req, Long id) {
        logManager.logDebug("UserServiceImpl:: login");
        AppUser user = userRepository.findById(id).orElseThrow(
                ()->new BusinessException("Existing user not available for userId: "+id));
        if(req.getAddress()!=null){
            user.setAddress(req.getAddress());
        }
        if(req.getRole()!=null){
            user.setRole(req.getRole());
        }
        if(req.getName()!=null){
            user.setName(req.getName());
        }
        if(req.getAge()!=null){
            user.setAge(req.getAge());
        }
        if(req.getEmail()!=null){
            user.setEmail(req.getEmail());
        }
        if(req.getPassword()!=null){
            user.setPassword(req.getPassword());
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails getUserById(Long id) {
        AppUser user = userRepository.findById(id).orElseThrow(()-> new BusinessException("User details not available for "+id));
        UserDetails userDetails= new UserDetails();
        userDetails.setAddress(user.getAddress());
        userDetails.setRole(user.getRole());
        userDetails.setId(user.getId());
        userDetails.setName(user.getName());
        userDetails.setAge(user.getAge());
        userDetails.setEmail(user.getEmail());
        return userDetails;
    }

    @Override
    public List<UserDetails> getAllUser() {
        logManager.logDebug("UserServiceImpl:: userService.getAllUsers");
        List<UserDetails> users = userRepository.findAll().stream()
                .map(a->{
                    UserDetails userDetails = new UserDetails();
                    userDetails.setAddress(a.getAddress());
                    userDetails.setRole(a.getRole());
                    userDetails.setId(a.getId());
                    userDetails.setName(a.getName());
                    userDetails.setAge(a.getAge());
                    userDetails.setEmail(a.getEmail());
                    return userDetails;
                }).toList();
        return users;
    }
}
