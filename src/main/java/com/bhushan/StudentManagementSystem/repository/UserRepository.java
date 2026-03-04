package com.bhushan.StudentManagementSystem.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bhushan.StudentManagementSystem.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}