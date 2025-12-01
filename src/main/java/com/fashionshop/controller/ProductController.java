package com.fashionshop.controller;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fashionshop.dto.product.ProductCreateRequest;
import com.fashionshop.dto.product.ProductDetailResponse;
import com.fashionshop.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService service;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreateRequest reqquest){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.createProduct(reqquest));
	}
	
//	dùng cho trang chủ và danh mục sản phẩm, có phân trang
	@GetMapping
	public ResponseEntity<Page<ProductDetailResponse>> getProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
		return ResponseEntity.ok(service.getAllProducts(page, size));
	}
	
//	dùng cho xem chỉ tiết của 1 sản phẩm dựa trên slug
	@GetMapping("/{slug}")
	public ResponseEntity<ProductDetailResponse> getProductBySlug(@PathVariable String slug){
		return ResponseEntity.ok(service.getProductBySlug(slug));
	}
}
