package com.fashionshop.config;

import com.fashionshop.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    
    // XÓA JwtFilter VÌ KHÔNG DÙNG NỮA

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Có thể tắt hoặc bật tùy bạn (Thymeleaf form hỗ trợ tốt CSRF)
            .authorizeHttpRequests(auth -> auth
                // 1. CHO PHÉP TÀI NGUYÊN TĨNH (CSS, JS, ẢNH)
                // Nếu không có dòng này, trang web sẽ không nhận CSS/JS
                .requestMatchers("/css/**", "/js/**", "/images/**", "/uploads/**", "/webjars/**").permitAll()
                
                // 2. CÁC TRANG CÔNG KHAI (Ai cũng xem được)
                .requestMatchers("/", "/home", "/login", "/register").permitAll()
                .requestMatchers("/product/**", "/category/**").permitAll() // Xem chi tiết SP
                .requestMatchers("/api/**").permitAll() // Nếu bạn vẫn dùng AJAX gọi API thì mở ra
                
                // 3. TRANG ADMIN
                .requestMatchers("/admin/**").hasRole("ADMIN")

                // 4. CÁC TRANG CÒN LẠI (Cart, Checkout, Profile...) -> Phải đăng nhập
                .anyRequest().authenticated()
            )
            
            // 5. CẤU HÌNH FORM LOGIN (Thay thế cho JWT)
            .formLogin(form -> form
                .loginPage("/login")              // Đường dẫn đến Controller hiển thị trang login
                .loginProcessingUrl("/perform_login") // URL mà form HTML sẽ submit vào (thẻ <form action="...">)
                .defaultSuccessUrl("/", true)     // Đăng nhập thành công thì về trang chủ
                .failureUrl("/login?error=true")  // Sai mật khẩu thì về lại login kèm lỗi
                .permitAll()
            )
            
            // 6. CẤU HÌNH LOGOUT
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .deleteCookies("JSESSIONID")
                .permitAll()
            );

        return http.build();
    }

    // Bean này giúp Spring Security tự động kết nối với CustomUserDetailsService của bạn
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}