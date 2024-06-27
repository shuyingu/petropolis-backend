package com.capstone.petropolis.controller;

import com.capstone.petropolis.service.ImageUploadService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile image) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String baseUrl = request.getRequestURL().toString();
        try {
            String url = imageUploadService.uploadImage(image);
            return ResponseEntity.ok(baseUrl + "/" + url);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
