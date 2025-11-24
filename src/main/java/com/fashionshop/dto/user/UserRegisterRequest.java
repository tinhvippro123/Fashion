package com.fashionshop.dto.user;

import java.time.LocalDate;

import com.fashionshop.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequest {
	private String username;
	private String email;
	private String password;
	private String fullName;
	private Gender gender;
	private LocalDate dateOfBirth;
	private String phone;

	
}
