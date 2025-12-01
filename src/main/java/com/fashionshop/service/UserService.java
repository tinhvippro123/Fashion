package com.fashionshop.service;

import java.util.List;
import com.fashionshop.dto.user.UserResponse;
import com.fashionshop.model.User;

public interface UserService {
	UserResponse getUserById(Long id);

	UserResponse getUserByEmail(String email);

	UserResponse getMyProfile();

	List<UserResponse> getAllUsers();

	User getCurrentUser();
}
