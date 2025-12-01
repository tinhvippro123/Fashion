package com.fashionshop.service;

import com.fashionshop.dto.auth.AuthResponse;
import com.fashionshop.dto.auth.LoginRequest;
import com.fashionshop.dto.auth.RegisterRequest;

public interface AuthService {
		void register(RegisterRequest request);
		AuthResponse login(LoginRequest request);
}
