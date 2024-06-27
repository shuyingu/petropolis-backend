package com.capstone.petropolis.service.impl;

import com.capstone.petropolis.service.ImageUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {
    private static final String IMAGE_DIR = "src/main/resources/tmp";

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        // save the image to the file system
        Path dest = Path.of(IMAGE_DIR, fileName);
        image.transferTo(dest);
        return fileName;
    }
}
