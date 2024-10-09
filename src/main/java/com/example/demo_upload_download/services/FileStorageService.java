package com.example.demo_upload_download.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${fileRepositoryFolder}")
    private String fileRepositoryFolder;

    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString() + "." + extension;
        File destination = new File(fileRepositoryFolder + "\\" + newFileName);
        file.transferTo(destination);

        return newFileName;
    }

    public byte[] download(String fileName) throws IOException {
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
    }

    public void remove(String fileName) throws IOException {
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        if(!fileFromRepository.exists()) return;
        boolean wasDeleted = fileFromRepository.delete();
        if (!wasDeleted) throw new IOException("Cannot delete file");
    }
}