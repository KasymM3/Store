// AuthController.java
package com.example.shop.controller;

import com.example.shop.entity.User;
import com.example.shop.payload.request.*;
import com.example.shop.payload.response.JwtResponse;
import com.example.shop.security.JwtTokenProvider;
import com.example.shop.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")

public class AuthController {
    private final AuthenticationManager authManager;
    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;

    public AuthController(AuthenticationManager authManager,
                          AuthService authService,
                          JwtTokenProvider tokenProvider) {
        this.authManager = authManager;
        this.authService = authService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest req) {
        authService.registerUser(req);
        return ResponseEntity.ok("User registered");
    }

    // src/main/java/com/example/shop/controller/AuthController.java
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        String jwt = tokenProvider.generateToken(auth);

        // берём реальные роли пользователя из базы
        User user  = authService.getByEmail(req.getEmail());
        List<String> roles = user.getRoles().stream()
                .map(r -> r.getName().name())
                .toList();

        return ResponseEntity.ok(new JwtResponse(jwt, roles));
    }


    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest req) {
        authService.resetPassword(req.getEmail(), req.getPassword());
        return ResponseEntity.ok(Map.of("message", "Пароль успешно сброшен"));
    }

    // For password reset: you'd send email with token link → separate endpoint to confirm.
}
