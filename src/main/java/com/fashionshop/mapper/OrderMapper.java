package com.fashionshop.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fashionshop.dto.order.OrderItemResponse;
import com.fashionshop.dto.order.OrderResponse;
import com.fashionshop.model.Order;
import com.fashionshop.model.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderMapper {
//	Order sang OrderResponse
	@Mapping(target = "orderDate", source = "orderDate",dateFormat = "dd-MM-yyyy HH:mm")
	@Mapping(target = "status", source = "status")
	@Mapping(target = "finalAmount", expression = "java(order.getTotalAmount() * (order.getShippingFee() != null ? order.getShippingFee() : 0))")
	@Mapping(target = "items", source = "orderItems")
	OrderResponse toOrderResponse(Order order);

	@Mapping(target = "subTotal", expression = "java(item.getUnitPrice() * item.getQuantity())")
	@Mapping(target = "variantId", source = "variant.id")
	OrderItemResponse toOrderItemResponse(OrderItem item);
	
	List<OrderItemResponse> toOrderItemResponses(List<OrderItem> items );
	
	default String getPaymentMethod(Order order) {
        if (order.getPayments() == null || order.getPayments().isEmpty()) {
            return "UNKNOWN";
        }
        // Lấy phương thức của giao dịch thanh toán đầu tiên (hoặc mới nhất)
        return order.getPayments().get(0).getPaymentMethod().name();
    }
}
