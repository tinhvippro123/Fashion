package com.fashionshop.controller;

import com.fashionshop.dto.auth.RegisterRequest;
import com.fashionshop.model.User;
import com.fashionshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 1. Trang Login
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Trả về templates/login.html
    }

    // 2. Trang Đăng ký
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "register"; // Trả về templates/register.html
    }

    // 3. Xử lý Đăng ký
    @PostMapping("/register")
    public String processRegister(@Valid @ModelAttribute("user") RegisterRequest request,
                                  BindingResult result,
                                  Model model) {
        // Validate dữ liệu
        if (result.hasErrors()) {
            return "register";
        }

        // Check trùng email
        if (userRepository.existsByEmail(request.getEmail())) {
            model.addAttribute("error", "Email đã tồn tại!");
            return "register";
        }

        // Tạo User
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(com.fashionshop.enums.Role.CUSTOMER); // Enum Role
        user.setIsActive(true);

        userRepository.save(user);

        return "redirect:/login?success"; // Chuyển sang trang login kèm thông báo
    }
}