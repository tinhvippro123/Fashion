package com.fashionshop.service;


import org.springframework.data.domain.Page;

import com.fashionshop.dto.product.ProductCreateRequest;
import com.fashionshop.dto.product.ProductDetailResponse;

public interface ProductService {
	ProductDetailResponse createProduct(ProductCreateRequest request);
	
//	Lấy tất cả sản phẩm có phân trang cho trang chủ/danh mục
	Page<ProductDetailResponse> getAllProducts(int page, int size);
	
//	Lấy chi tiết 1 sản phẩm theo slug cho trang detail
	ProductDetailResponse getProductBySlug(String slug);
	
}
