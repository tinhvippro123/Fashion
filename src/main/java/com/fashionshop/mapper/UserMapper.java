package com.fashionshop.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fashionshop.dto.user.UserRegisterRequest;
import com.fashionshop.dto.user.UserResponse;
import com.fashionshop.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

//	public static User fromRegisterRequest(UserRegisterRequest request) {
//		return User.builder().username(request.getUsername()).email(request.getEmail())
//				.passwordHash(request.getPassword()).fullName(request.getFullName()).gender(request.getGender())
//				.dateOfBirth(request.getDateOfBirth()).phone(request.getPhone()).build();
//	}
//
//	public static UserResponse toResponse(User user) {
//
//		return new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getGender(),
//				user.getDateOfBirth(), user.getPhone(), user.getRole(), user.getIsActive(), user.getCreatedAt());
//	}
	
	@Mapping(target = "passwordHash", source = "password")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "username", ignore = true)
	@Mapping(target = "role", constant = "CUSTOMER")
	@Mapping(target = "isActive", constant = "true")
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "addresses", ignore = true)
	User fromRegisterRequest(UserRegisterRequest request);
		
	UserResponse toUserResponse(User user);
}
