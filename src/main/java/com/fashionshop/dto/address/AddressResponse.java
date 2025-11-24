package com.fashionshop.dto.address;

import lombok.Data;

@Data
public class AddressResponse {
	private Long id;
	private String receiverName;
	private String phone;
	private String province;
	private String district;
	private String ward;
	private String street;
	private Boolean isDefault;
}
