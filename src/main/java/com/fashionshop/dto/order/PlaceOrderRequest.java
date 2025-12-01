package com.fashionshop.dto.order;

import com.fashionshop.enums.PaymentMethod;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlaceOrderRequest {
	@NotBlank(message = "Receiver name must not be empty")
	private String receiverName;

	@NotBlank(message = "Phone number must not be empty")
	private String phone;

	@NotBlank(message = "Province/City must not be empty")
	private String province;

	@NotBlank(message = "District must not be empty")
	private String district;

	@NotBlank(message = "Ward must not be empty")
	private String ward;

	@NotBlank(message = "Detailed address must not be empty")
	private String street;

	// Payment method
	@NotNull(message = "Payment method must be selected")
	private PaymentMethod paymentMethod;

}
