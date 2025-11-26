package com.fashionshop.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartItemRequest {
	
	@NotNull(message = "A product must be selected")
	private Long variantId;
	
	@NotNull(message = "Quantity must not be empty")
	@Min(value = 1, message = "The minimum purchase quantity is 1")
	private Integer quantity;
}
