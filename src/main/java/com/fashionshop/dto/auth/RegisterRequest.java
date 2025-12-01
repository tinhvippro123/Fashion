package com.fashionshop.dto.auth;

import com.fashionshop.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	@NotBlank(message = "Username must not be empty")
	private String username;

	@NotBlank(message = "Email must not be empty")
	@Email(message = "Invalid email format")
	private String email;

	@NotBlank(message = "Password must not be empty")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;

    private String fullName;
    private String phone;
    
    private Gender gender;
    private LocalDate dateOfBirth;
}