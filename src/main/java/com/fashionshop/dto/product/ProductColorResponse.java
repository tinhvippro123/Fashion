package com.fashionshop.dto.product;

import java.util.List;

import lombok.Data;

@Data
public class ProductColorResponse {
	private Long id;
	private Long colorId;
	private String colorName;
	private String hexCode;
	private Boolean isDefault;
	
	private List<ImageResponse> images;
	private List<VariantResponse> variants;
}
