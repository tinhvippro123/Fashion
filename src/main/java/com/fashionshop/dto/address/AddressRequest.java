package com.fashionshop.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddressRequest {
//	Chưa có spring security lấy user từ token thì tam thời đưa userId vào body
	private Long userId;
	
	@NotBlank(message = "Receiver name is required")
	private String receiverName;
	
	@NotBlank(message = "Phone name is required")
	private String phone;

	@NotBlank(message = "province name is required")
	private String province;
	
	@NotBlank(message = "district name is required")
	private String district;
	
	@NotBlank(message = "ward name is required")
	private String ward;
	
	@NotBlank(message = "street name is required")
	private String street;
	
	private Boolean isDefault;
	
}
