package com.bhushan.StudentManagementSystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())        // ⭐ allow POST/PUT/DELETE
            .cors(cors -> {})                    // ⭐ allow React
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/**").permitAll() // allow all API calls
                    .anyRequest().permitAll()
            )
            .formLogin(form -> form.disable());  // disable login page

        return http.build();
    }
}