package com.fashionshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fashionshop.dto.address.AddressRequest;
import com.fashionshop.dto.address.AddressResponse;
import com.fashionshop.model.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {
	@Mapping(target = "user", ignore = true)
	Address toAddress(AddressRequest request);
	
	AddressResponse toAddressResponse(Address address);	
}
