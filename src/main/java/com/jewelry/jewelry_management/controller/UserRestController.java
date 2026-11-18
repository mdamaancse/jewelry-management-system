package com.jewelry.jewelry_management.controller;

import com.jewelry.jewelry_management.entity.User;
import com.jewelry.jewelry_management.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRestController(UserRepository userRepository,
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        if (user.getName() == null || user.getName().isBlank()
                || user.getEmail() == null || user.getEmail().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "All fields are required"));
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "Email already registered"));
        }

        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                Map.of(
                        "id", savedUser.getId(),
                        "name", savedUser.getName(),
                        "email", savedUser.getEmail(),
                        "message", "Registration successful"
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request,
                                   HttpSession session) {

        String email = request.get("email");
        String password = request.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Email and password are required"));
        }

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        session.setAttribute("userId", user.getId());

        return ResponseEntity.ok(
                Map.of("message", "Login successful", "userId", user.getId())
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Please login first"));
        }

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        return ResponseEntity.ok(
                Map.of(
                        "id", user.getId(),
                        "name", user.getName(),
                        "email", user.getEmail()
                )
        );
    }
}