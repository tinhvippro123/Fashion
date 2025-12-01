package com.fashionshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fashionshop.dto.order.OrderResponse;
import com.fashionshop.dto.order.PlaceOrderRequest;
import com.fashionshop.enums.OrderStatus;
import com.fashionshop.model.Cart;
import com.fashionshop.model.Order;

@Service
public interface OrderService {
	Order placeOrder(Long userId, String sessionId, PlaceOrderRequest request);
	
	Cart getCart(Long userId, String sessionId);
	
	List<OrderResponse> getMyOrders(Long userId); // Cái này cho khách xem
	List<OrderResponse> getAllOrders(); // Lấy cho admin xem
	
//	Cái này là để admin cập nhật trạng thái đơn hàng
	Order updateOrderStatus(Long orderId, OrderStatus newStatus);
}
