package com.fashionshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashionshop.model.Color;
import com.fashionshop.repository.ColorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/colors")
@RequiredArgsConstructor
public class ColorController {
	private final ColorRepository colorRepository;
	
	@GetMapping
	public ResponseEntity<List<Color>> getAllColors() {
		return ResponseEntity.ok(colorRepository.findAll());
	}
	
	@PostMapping
	public ResponseEntity<Color> createColor(@RequestBody Color color){
		return ResponseEntity.status(HttpStatus.CREATED).body(colorRepository.save(color));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteColor(@PathVariable Long id){
		colorRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
