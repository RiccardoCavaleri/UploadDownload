package com.example.demo_upload_download.controllers;

import com.example.demo_upload_download.services.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/es")
public class FileStorageController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        return fileStorageService.upload(file);
    }

    @GetMapping("/download")
    public byte[] download(@RequestParam String fileName, HttpServletResponse response) throws IOException {
        String extension = FilenameUtils.getExtension(fileName);
        switch (extension) {
            case "gif":
                response.setContentType(MediaType.IMAGE_GIF_VALUE);
                break;
            case "jpg":
            case "jpeg":
                response.setContentType(MediaType.IMAGE_JPEG_VALUE);
                break;
            case "png":
                response.setContentType(MediaType.IMAGE_PNG_VALUE);
                break;
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        return fileStorageService.download(fileName);
    }

    @DeleteMapping("/remove")
    public void remove(@RequestParam String fileName) throws IOException {
        fileStorageService.remove(fileName);
    }
}