package com.fashionshop.service.implement;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fashionshop.dto.auth.AuthResponse;
import com.fashionshop.dto.auth.LoginRequest;
import com.fashionshop.dto.auth.RegisterRequest;
import com.fashionshop.mapper.UserMapper;
import com.fashionshop.model.User;
import com.fashionshop.repository.UserRepository;
import com.fashionshop.security.JwtUtils;
import com.fashionshop.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequestMapping
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtils jwtUtils;
	private final UserMapper userMapper;

	@Override
	public void register(RegisterRequest request) {
		if(userRepository.existsByEmail((request.getEmail()))){
			throw new RuntimeException("Email already exists");
		}
		
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new RuntimeException("Registration successful");
		}
		
		User user = userMapper.fromRegisterRequest(request);
		
		user.setPasswordHash(passwordEncoder	.encode(request.getPassword()));
		
		userRepository.save(user);
	}
	@Override
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		
		String token =  jwtUtils.generateToken(request.getEmail());
		
		return new AuthResponse(token);
	}
	
	
}
