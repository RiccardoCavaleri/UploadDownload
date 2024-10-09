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
    // percorso della cartella che uso come repository
    @Value("${fileRepositoryFolder}")
    private String fileRepositoryFolder;

    public String upload(MultipartFile file) throws IOException {
        // mi salvo l'estensione del file
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        // creo un nuovo nome per il file
        String newFileName = UUID.randomUUID().toString() + "." + extension;
        // creo un oggetto per indicare il file nella cartella di repository
        File destination = new File(fileRepositoryFolder + "\\" + newFileName);
        // salvo il file nella repository
        file.transferTo(destination);
        // ritorno il nuovo nome del file
        return newFileName;
    }

    public byte[] download(String fileName) throws IOException {
        // creo un oggetto per indicare il file nella cartella di repository
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        // faccio una conversione del mio file a byte
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
    }

    public void remove(String fileName) throws IOException {
        // creo un oggetto per indicare il file nella cartella di repository
        File fileFromRepository = new File(fileRepositoryFolder + "\\" + fileName);
        // se il file non esiste non devo fare niente
        if(!fileFromRepository.exists()) return;
        // elimino il file e controllo se è stato eliminato
        boolean wasDeleted = fileFromRepository.delete();
        if (!wasDeleted) throw new IOException("Cannot delete file");
    }
}