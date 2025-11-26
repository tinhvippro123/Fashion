package com.fashionshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fashionshop.dto.cart.CartItemRequest;
import com.fashionshop.dto.cart.CartResponse;
import com.fashionshop.service.CartService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService service;

	// Xem giỏ hàng
	@GetMapping
	public 	ResponseEntity<CartResponse> getCart(@RequestParam(required = false) Long userId, @RequestHeader(value = "X-Session-id", required = false) String sessionId){
		return ResponseEntity.ok(service.getCart(userId, sessionId));
	}
	
//	Thêm vào giỏ nè
	@PostMapping("/add")
	public ResponseEntity<CartResponse> addToCart(
			@RequestParam(required = false) Long userId, 
			@RequestHeader(value = "X-Session-id", required = false) String sessionId,
			@RequestBody CartItemRequest request){
		return ResponseEntity.ok(service.addToCart(userId, sessionId, request));
	}

	@DeleteMapping("/remove/{itemId}")
	public ResponseEntity<CartResponse> removeFromCart(
			@PathVariable Long itemId,
			@RequestParam(required = false) Long userId,
			@RequestHeader(value = "X-Session-id", required = false) String sessionId){
		return ResponseEntity.ok(service.removeFromCart(userId, sessionId, itemId));
	}
	
//	Cập nhật số lượng item
	@PutMapping("/update/{itemId}")
	public ResponseEntity<CartResponse> updateCartItem(
			@PathVariable Long itemId,
			@RequestParam Integer quantity,
			@RequestParam(required = false) Long userId,
			@RequestHeader(value = "X-Session-id", required = false) String sessionIdD){
		return ResponseEntity.ok(service.updateItemQuantity(userId, sessionIdD, itemId, quantity));
	}
	
}
