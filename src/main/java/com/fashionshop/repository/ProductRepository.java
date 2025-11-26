package com.fashionshop.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fashionshop.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	// Tìm kiếm sản phẩm theo slug
	Optional<Product> findBySlug(String slug);
	
	// KIểm tra xem slug đã tồn tại hay chưa
	boolean existsBySlug(String slug);
	
	Page<Product> findByIsActiveTrue(Pageable pageable);
}
