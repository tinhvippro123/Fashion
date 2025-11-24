package com.fashionshop.service;

import java.util.List;

import com.fashionshop.dto.address.AddressRequest;
import com.fashionshop.dto.address.AddressResponse;

public interface AddressService {
	AddressResponse createAddress(AddressRequest request);
	List<AddressResponse> getAddressByUserId(Long userId);
	
}
