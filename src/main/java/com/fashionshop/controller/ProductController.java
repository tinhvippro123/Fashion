package com.fashionshop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fashionshop.dto.product.ProductCreateRequest;
import com.fashionshop.dto.product.ProductDetailResponse;
import com.fashionshop.service.implement.ProductServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductServiceImpl service;
	
	@PostMapping
	public ResponseEntity<ProductDetailResponse> createProduct(@Valid @RequestBody ProductCreateRequest reqquest){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(reqquest));
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDetailResponse>> getAllProduct(){
		return ResponseEntity.ok(service.getAllProduct());
	}
	
	@GetMapping("/{slug}")
	public ResponseEntity<ProductDetailResponse> getProductBySlug(String slug){
		return ResponseEntity.ok(service.getProductBySlug(slug));
	}
}
