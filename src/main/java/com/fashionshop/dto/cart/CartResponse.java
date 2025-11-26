package com.fashionshop.dto.cart;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponse {
	private Long id;
	private Double totalAmount;
	private List<CartItemResponse> items;
	
	@Data
	@Builder
	public static class CartItemResponse{
		private Long itemId;
		private Long variantId;
		private String productName;
		private String colorName;
		private String sizeName;
		private String imageUrl;
		private Double price;
		private Integer quantity;
		private Double subTotal; // Thành tiền 
	}
}
