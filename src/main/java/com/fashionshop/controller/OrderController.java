package com.fashionshop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fashionshop.dto.order.OrderResponse;
import com.fashionshop.dto.order.PlaceOrderRequest;
import com.fashionshop.enums.OrderStatus;
import com.fashionshop.model.Order;
import com.fashionshop.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService service;
	
	@PostMapping("/place-order")
	public ResponseEntity<Order> placeOrder(@RequestParam(required = false) Long userId, @RequestHeader String sessionId, @Valid @RequestBody PlaceOrderRequest request){
		return ResponseEntity.ok(service.placeOrder(userId, sessionId, request));
	}
	
//	Xem lịch sử giỏ hàng
	@GetMapping("/my-orders")
	public ResponseEntity<List<OrderResponse>> getMyOrders(@RequestParam Long userId){
		return ResponseEntity.ok(service.getMyOrders(userId));
	}
	
	@GetMapping("/admin")
	public ResponseEntity<List<OrderResponse>> getAllOrders(){
		return ResponseEntity.ok(service.getAllOrders());
	}
	
	@PutMapping("{id}/status")
	public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status){
		return ResponseEntity.ok(service.updateOrderStatus(id, status));
	}
}
