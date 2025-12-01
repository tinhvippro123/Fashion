package com.fashionshop.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    
    // Có thể thêm các field khác nếu muốn frontend tiện xử lý
    // private String refreshToken;
    // private String tokenType = "Bearer";
    // private String username;
    // private String role;
}