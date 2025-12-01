package com.fashionshop.service;

import java.util.List;
import com.fashionshop.dto.user.UserResponse;

public interface UserService {
	public UserResponse getUserById(Long id);
	public UserResponse getUserByUsername(String usernane);
	public UserResponse getMyProfile();
	public List<UserResponse> getAllUsers();
}
