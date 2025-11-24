package com.fashionshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashionshop.model.Size;
import com.fashionshop.repository.SizeRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sizes")
@RequiredArgsConstructor
public class SizeController {
	private final SizeRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Size>> getAllSizes(){
		return ResponseEntity.ok(repository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Size> createSize(@RequestBody Size size){
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(size));
	}
}
