package com.fashionshop.controller;

import com.fashionshop.service.FileStorageService; // Gọi Interface
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService storageService; // Gọi Interface

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        String fileName = storageService.storeFile(file);
        String url = "http://localhost:8080/images/" + fileName;
        return ResponseEntity.ok(Map.of("url", url));
    }
}