package com.fashionshop.dto.order;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemResponse {
	private Long id;          
    private Long variantId;    
    private String productName;
    private String colorName;
    private String sizeName;
    private String productImage;
    private Integer quantity;
    private Double unitPrice;
    private Double subTotal; 
}
