package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MultimediaService {
    void uploadImage(MultipartFile file, Product product) throws IOException;

    ResponseEntity<String> deleteMultimedia(Long id);

}
