package com.fashionshop.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    
	@NotBlank(message = "Email must not be empty")
	private String email;

	@NotBlank(message = "Password must not be empty")
	private String password;

}