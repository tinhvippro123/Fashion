package com.fashionshop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashionshop.dto.address.AddressRequest;
import com.fashionshop.dto.address.AddressResponse;
import com.fashionshop.service.AddressService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/addresses")
public class AddressController {
	private final AddressService service;
	
	@PostMapping
	public ResponseEntity<AddressResponse> createAddress(@Valid @RequestBody AddressRequest request){
		return ResponseEntity.ok(service.createAddress(request));
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<AddressResponse>> getUserAddress(@PathVariable Long userId){
		return ResponseEntity.ok(service.getAddressByUserId(userId));
	}
}
