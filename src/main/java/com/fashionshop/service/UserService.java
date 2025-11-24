package com.fashionshop.service;

import java.util.List;

import com.fashionshop.dto.user.UserRegisterRequest;
import com.fashionshop.dto.user.UserResponse;

public interface UserService {
	public UserResponse register(UserRegisterRequest request); // create user
	public UserResponse getUserById(Long id);
	public UserResponse getUserByUsername(String usernane);
	public List<UserResponse> getAllUsers();
}
