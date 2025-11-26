package com.fashionshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fashionshop.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	Optional<Cart> findByUserId(Long userId);
	
	Optional<Cart> findBySessionId(String sessionId);
	
//	Cài này xóa giỏ hàng theo sessionId dùng khi merge hoặc dọn rác
	void deleteBySessionId(String sessionId);
}
