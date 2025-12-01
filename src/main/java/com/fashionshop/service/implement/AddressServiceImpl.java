package com.fashionshop.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionshop.dto.address.AddressRequest;
import com.fashionshop.dto.address.AddressResponse;
import com.fashionshop.mapper.AddressMapper;
import com.fashionshop.model.Address;
import com.fashionshop.model.User;
import com.fashionshop.repository.AddressRepository;
import com.fashionshop.repository.UserRepository;
import com.fashionshop.service.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
	private final AddressRepository addressRepository;
	private final UserRepository userRepository;
	private final AddressMapper addressMapper;
	
	@Override
	@Transactional
	public AddressResponse createAddress(AddressRequest request) {
		User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
		
		Address address = addressMapper.toAddress(request);
		address.setUser(user);
		
		// xử lý địa chỉ mặc định
		if(Boolean.TRUE.equals(request.getIsDefault())) {
			
			List<Address> existingAddress = addressRepository.findByUserId(user.getId());
			for (Address addr : existingAddress) {
				addr.setIsDefault(false);
			}
			addressRepository.saveAll(existingAddress);
		} else {
			List<Address> existingAddress = addressRepository.findByUserId(user.getId());
			if(existingAddress.isEmpty()) {
				address.setIsDefault(true);
			}
		}
		
		Address savedAddress = addressRepository.save(address);
	
		return addressMapper.toAddressResponse(savedAddress);
	}

	@Override
	public List<AddressResponse> getAddressByUserId(Long userId) {
		return addressRepository.findByUserId(userId).stream().map(addressMapper::toAddressResponse).collect(Collectors.toList());
	}

}
