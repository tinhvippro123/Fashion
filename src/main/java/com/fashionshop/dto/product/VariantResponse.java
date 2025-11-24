package com.fashionshop.dto.product;

import lombok.Data;

@Data
public class VariantResponse {
	private Long id;
	private Long sizeId;
	private String sizeName;
	private Double price;
	private Integer stock;

}
