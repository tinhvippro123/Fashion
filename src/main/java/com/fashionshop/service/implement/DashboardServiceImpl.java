package com.fashionshop.service.implement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fashionshop.dto.dashboard.DashboardResponse;
import com.fashionshop.mapper.OrderMapper;
import com.fashionshop.model.Order;
import com.fashionshop.repository.OrderRepository;
import com.fashionshop.repository.ProductRepository;
import com.fashionshop.repository.UserRepository;
import com.fashionshop.service.DashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {
	private final OrderRepository orderRepository;
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	private final OrderMapper orderMapper;

	@Override
	public DashboardResponse getDashboardData() {
//		Tính ngày giờ
		LocalDateTime startOfToday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
		LocalDateTime startOfMonth = LocalDate.now().withDayOfYear(1).atStartOfDay(); // Ngày 1 đầu tháng
		LocalDateTime endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth())
				.atTime(LocalTime.MAX); // Cuối tháng nè

//		Query lấy dữ liệu
		Double revenue = orderRepository.calculateRevenue(startOfMonth, endOfMonth);
		Long newOrders = orderRepository.countByOrderDateAfter(startOfToday);
		Long totalProducts = productRepository.count();
		Long totalUsers = userRepository.count();
		List<Order> recentOrders = orderRepository.findTop10ByOrderByOrderDateDesc();

		return DashboardResponse.builder().monthlyRevenue(revenue != null ? revenue : 0.0).newOrderCount(newOrders)
				.totalProducts(totalProducts).totalUsers(totalUsers)
				.recentOrders(recentOrders.stream().map(orderMapper::toOrderResponse).collect(Collectors.toList()))
				.build();
	}

}
