package com.fashionshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashionshop.model.Color;
import com.fashionshop.service.ColorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/colors")
@RequiredArgsConstructor
public class ColorController {
	private final ColorService service;
	
	@GetMapping
	public ResponseEntity<List<Color>> getAllColors() {
		return ResponseEntity.ok(service.getAll());
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Color> createColor(@RequestBody Color color){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(color));
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Color> updateColor(@PathVariable Long id, @RequestBody Color request){
		return ResponseEntity.ok(service.update(id, request));
	}
	
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteColor(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
