package com.fashionshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fashionshop.model.ProductColor;

public interface ProductColorRepository extends JpaRepository<ProductColor, Long>{
	boolean existsByColorId(Long colorId);
}
