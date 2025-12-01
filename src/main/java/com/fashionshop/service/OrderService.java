package com.fashionshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fashionshop.dto.order.OrderResponse;
import com.fashionshop.dto.order.PlaceOrderRequest;
import com.fashionshop.enums.OrderStatus;

@Service
public interface OrderService {
	OrderResponse placeOrder(String sessionId, PlaceOrderRequest request);
	
	List<OrderResponse> getMyOrders(); // Cái này cho khách xem
	List<OrderResponse> getAllOrders(); // Lấy cho admin xem
	
//	Cái này là để admin cập nhật trạng thái đơn hàng
	OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus);
}
