package com.fashionshop.service;

import java.util.List;

import com.fashionshop.dto.product.ProductCreateRequest;
import com.fashionshop.dto.product.ProductDetailResponse;

public interface ProductService {
	ProductDetailResponse createProduct(ProductCreateRequest request);
	
	List<ProductDetailResponse> getAllProduct();
	
	ProductDetailResponse getProductBySlug(String slug);
	
}
