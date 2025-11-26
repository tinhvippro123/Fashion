package com.fashionshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashionshop.dto.user.UserRegisterRequest;
import com.fashionshop.dto.user.UserResponse;
import com.fashionshop.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService service;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRegisterRequest request){
//		UserResponse response = service.register(request);
//		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		return ResponseEntity.status(HttpStatus .CREATED).body(service.register(request));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.getUserById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUser(){
		return ResponseEntity.ok(service.getAllUsers());
	}
}
