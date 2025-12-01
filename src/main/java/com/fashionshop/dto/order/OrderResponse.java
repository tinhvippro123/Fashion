package com.fashionshop.dto.order;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
	private Long id;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;
    private Double totalAmount;
    private Double shippingFee;
    private Double finalAmount;
    private String status;
    private Boolean paymentStatus;
    private String paymentMethod;
    private String orderDate;    // Format ngày tháng đẹp đẽ
    private List<OrderItemResponse> items;
}
