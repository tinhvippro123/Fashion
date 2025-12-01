package com.fashionshop.mapper;


//import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//import org.mapstruct.NullValuePropertyMappingStrategy;

import com.fashionshop.dto.auth.RegisterRequest;
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
	User fromRegisterRequest(RegisterRequest request);
		
	UserResponse toUserResponse(User user);
	
//	// Dùng cho API Update Profile
//	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // Nếu request gửi field null thì giữ nguyên giá trị cũ trong DB, không set null theo
//	@Mapping(target = "id", ignore = true)          // Không cho sửa ID
//	@Mapping(target = "username", ignore = true)    // Thường username không cho sửa
//	@Mapping(target = "passwordHash", ignore = true)// Password sửa API riêng
//	@Mapping(target = "role", ignore = true)        // User không tự sửa role được
//	void updateUserFromRequest(UserRegisterRequest request, @MappingTarget User user);
}
