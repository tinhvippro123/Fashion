package com.fashionshop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fashionshop.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
	
	List<Order> findAllByOrderByOrderDateDesc();

//	Tính tổng tiền mấy cái đơn hàng đã giao trong tháng này
	@Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.status = 'DELIVERED' AND o.orderDate BETWEEN :startDate AND :endDate")
    Double calculateRevenue(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

//	Đếm số đơn hàng mỗi ngày
	Long countByOrderDateAfter(LocalDateTime date);
	
	List<Order> findTop10ByOrderByOrderDateDesc();
}


