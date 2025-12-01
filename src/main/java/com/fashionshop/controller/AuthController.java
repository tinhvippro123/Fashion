package com.fashionshop.controller;

import com.fashionshop.dto.auth.LoginRequest;
import com.fashionshop.dto.auth.RegisterRequest;
import com.fashionshop.dto.auth.AuthResponse;
import com.fashionshop.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService service;
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request){
		service.register(request);
		return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful");
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
		return ResponseEntity.ok(service.login(request));
	}
}