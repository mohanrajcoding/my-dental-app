package com.auth_service.repository;

import com.auth_service.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<AppUser,Long> {
        AppUser findByEmail(String email);
}
