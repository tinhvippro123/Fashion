package com.fashionshop.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fashionshop.dto.user.UserRegisterRequest;
import com.fashionshop.dto.user.UserResponse;
import com.fashionshop.mapper.UserMapper;
import com.fashionshop.model.User;
import com.fashionshop.repository.UserRepository;
import com.fashionshop.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final UserMapper userMapper;
	

	@Override
	@Transactional
	public UserResponse register(UserRegisterRequest request) {
		if(repository.existtByUserName(request.getUsername())) {
			throw new IllegalArgumentException("Tài khoản đã tồn tại");
		}
		
		if(repository.existByEmail(request.getEmail())) {
			throw new IllegalArgumentException("Email đã tồn tại");
		}
		
		User user = userMapper.fromRegisterRequest(request);
		user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		User saved = repository.save(user);
		return userMapper.toUserResponse(saved);
	}

//	public String login(String username, String email, ) {
//		User user = repository.findByUsernameOrEmail(username, email) ->;
//		
//	}
	@Override
	public UserResponse getUserById(Long id) {
		User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		
		return userMapper.toUserResponse(user);
	}

	@Override
	public UserResponse getUserByUsername(String usernane) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserResponse> getAllUsers() {
		
		return repository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
	}

}
