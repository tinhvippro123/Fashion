package com.fashionshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Tắt CSRF (Cross-Site Request Forgery) để test được POST trên Postman
            .csrf(csrf -> csrf.disable()) 
            
            // Cấu hình quyền truy cập
            .authorizeHttpRequests(auth -> auth
                // Cho phép truy cập tự do vào các đường dẫn bắt đầu bằng /api/colors
            		.requestMatchers("/api/colors/**", "/api/sizes/**", "/api/categories/**", "/api/products/**", "/api/upload/**", "/api/cart/**").permitAll()
                
                // Các request khác bắt buộc phải đăng nhập (tùy nhu cầu của bạn)
                .anyRequest().authenticated() 
            );

        return http.build();
    }
}
