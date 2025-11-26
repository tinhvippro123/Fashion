package com.fashionshop.service;


import org.springframework.data.domain.Page;

import com.fashionshop.dto.product.ProductCreateRequest;
import com.fashionshop.dto.product.ProductDetailResponse;

public interface ProductService {
	ProductDetailResponse createProduct(ProductCreateRequest request);
	
	Page<ProductDetailResponse> getAllProducts(int page, int size);
	
	ProductDetailResponse getProductBySlug(String slug);
	
}
