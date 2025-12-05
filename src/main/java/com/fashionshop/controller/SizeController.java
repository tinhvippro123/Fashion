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

import com.fashionshop.model.Size;
import com.fashionshop.service.SizeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sizes")
@RequiredArgsConstructor
public class SizeController {
	private final SizeService service;

	@GetMapping
	public ResponseEntity<List<Size>> getAllSizes() {
		return ResponseEntity.ok(service.getAll());
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Size> createSize(@RequestBody Size size) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.create(size));
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Size> updateSize(@PathVariable Long id, @RequestBody Size size) {
		return ResponseEntity.ok(service.update(id, size));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteSize(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
