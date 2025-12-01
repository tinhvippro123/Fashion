package com.fashionshop.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
	private final UserMapper userMapper;
	
	@Override
	public UserResponse getUserById(Long id) {
		User user = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
		
		return userMapper.toUserResponse(user);
	}

	@Override
	public UserResponse getUserByEmail(String email) {
		User user = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email));
		return userMapper.toUserResponse(user);
	}

	@Override
	public List<UserResponse> getAllUsers() {
		
		return repository.findAll().stream().map(userMapper::toUserResponse).collect(Collectors.toList());
	}

	@Override
	public UserResponse getMyProfile() {
//		Cái này là để lấy enmail từ security context(lấy email từ token)
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
		return userMapper.toUserResponse(user);
	}

	@Override
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication==null||!authentication.isAuthenticated()|| authentication.getPrincipal().equals("anonymousUser")) {
			return null; // nghĩa là chưa đăng nhập thì return null
		}
		String email = authentication.getName();
		return repository.findByEmail(email).orElse(null);
	}

}
