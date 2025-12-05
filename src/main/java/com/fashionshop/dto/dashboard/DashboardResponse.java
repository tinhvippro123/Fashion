package com.fashionshop.dto.dashboard;

import java.util.List;

import com.fashionshop.dto.order.OrderResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardResponse {
	private Double monthlyRevenue;
	private Long newOrderCount;
	private Long totalProducts;
	private Long totalUsers;
	private List<OrderResponse> recentOrders;
}
