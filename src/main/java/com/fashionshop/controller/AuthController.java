package com.fashionshop.controller;

import com.fashionshop.dto.auth.LoginRequest;
import com.fashionshop.dto.auth.RegisterRequest;
import com.fashionshop.dto.auth.AuthResponse;
import com.fashionshop.model.User;
import com.fashionshop.enums.Role;
import com.fashionshop.repository.UserRepository;
import com.fashionshop.security.JwtUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    // 1. Đăng ký
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = User.builder()
                .username(request.getUsername()) // Lưu username
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phone(request.getPhone())       // Lưu sđt
                .gender(request.getGender())     // Lưu giới tính
                .dateOfBirth(request.getDateOfBirth()) // Lưu ngày sinh
                .role(Role.CUSTOMER)
                .isActive(true)
                .build();
        
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
    }

    // 2. Đăng nhập
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Xác thực username/password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Nếu đúng thì tạo Token trả về
        String token = jwtUtils.generateToken(request.getEmail());
        
        return ResponseEntity.ok(new AuthResponse(token));
    }
}