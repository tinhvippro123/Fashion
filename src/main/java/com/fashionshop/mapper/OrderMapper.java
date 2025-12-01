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
	@Mapping(target = "paymentMethod", expression= "java(getPaymentMethod(order)")
	@Mapping(target = "paymentStatus", expression= "java(getPaymentStatus(order)")
	@Mapping(target = "receiverAddress", expression = "java(buildAddress(order)")
	@Mapping(target = "receiverPhone", source = "phone")
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
	
	// Hàm lấy Payment Status
    default Boolean getPaymentStatus(Order order) {
        if (order.getPayments() == null || order.getPayments().isEmpty()) {
            return false;
        }
        return order.getPayments().get(0).getPaymentStatus();
    }
	
	default String buildAddress(Order order) {
        // Ví dụ: "123 Xuan Thuy, Dich Vong, Cau Giay, Ha Noi"
        return (order.getStreet() != null ? order.getStreet() : "") + ", " +
               (order.getWard() != null ? order.getWard() : "") + ", " +
               (order.getDistrict() != null ? order.getDistrict() : "") + ", " +
               (order.getProvince() != null ? order.getProvince() : "");
    }
}
