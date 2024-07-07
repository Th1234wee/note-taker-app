package com.api.note.utils.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;


@Service
public class ImageHandler {
    @Value("${upload.folder}")
    private String uploadFolder;

    public String ImageProcessHandler(MultipartFile imageUrl){
        Date currentDate = new Date();
        String imageName = currentDate.getTime() + "-" + imageUrl.getOriginalFilename();
        try{
            Path pathForUpload = Paths.get(uploadFolder);
            Files.createDirectories(pathForUpload);
            try(InputStream inputStream = imageUrl.getInputStream()){
                Files.copy(inputStream , Paths.get(uploadFolder , imageName));
            }
            return imageName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
