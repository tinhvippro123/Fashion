package com.fashionshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// Cấu hình: Khi truy cập đường dẫn http://localhost:8080/images/**
		// Sẽ tìm file trong thư mục "uploads" ở gốc dự án

		String uploadPath = Paths.get("uploads").toFile().getAbsolutePath();

		registry.addResourceHandler("/images/**").addResourceLocations("file:" + uploadPath + "/");
	}

	public void addCorsMappings(CorsRegistry register) {
		register.addMapping("/api/**").allowedOrigins("http://localhost:3000", "http://localhost:5173")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(true);
	}
}