package com.fashionshop.dto.product;

import java.util.List;

import lombok.Data;

@Data
public class ProductDetailResponse {
	private Long id;
	private String name;
	private String slug;
	private Double basePrice;
	private String description;
	private String categoryName;
	
	// List các nhóm màu
	private List<ProductColorResponse> colors;
}
