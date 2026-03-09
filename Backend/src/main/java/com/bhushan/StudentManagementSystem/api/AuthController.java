package com.bhushan.StudentManagementSystem.api;

import org.springframework.web.bind.annotation.*;
import com.bhushan.StudentManagementSystem.repository.UserRepository;
import com.bhushan.StudentManagementSystem.dto.LoginRequest;
import com.bhushan.StudentManagementSystem.entity.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository repo;

    public AuthController(UserRepository repo) {
        this.repo = repo;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {

        User user = repo.findByUsername(request.username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(request.password))
            throw new RuntimeException("Invalid password");

        // TEMP (later JWT)
        return "LOGIN_SUCCESS_" + user.getRole();
    }
}