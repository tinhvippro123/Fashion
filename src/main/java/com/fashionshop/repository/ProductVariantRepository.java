package com.fashionshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fashionshop.model.ProductVaraint;

@Repository
public interface ProductVariantRepository extends JpaRepository<ProductVaraint, Long>{
	boolean existsBySizeId(Long sizeId);
}
