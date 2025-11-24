package com.fashionshop.dto.user;

import java.time.LocalDate;

import com.fashionshop.enums.Gender;
import com.fashionshop.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
	private Long id;
	private String username;
	private String email;
	private String fullName;
	private Gender gender;
	private LocalDate dateOfBirth;
	private String phone;
	private Role role;
	private boolean isActive;
}
