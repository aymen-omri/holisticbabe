package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Repositories.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Services.MultimediaService;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MultimediaServiceImpl implements MultimediaService {
    
    private final MultimediaRepository multimediaRepository;
    private final FileUploadService fileUploadService;


    @Override
    public void uploadImages(MultipartFile[] files, Product product) throws IOException {
        for (MultipartFile file : files) {
            String url = fileUploadService.uploadFile(file,"product-multimedia");
            Multimedia multimedia = new Multimedia();
            multimedia.setUrl(url);
            multimedia.setName(file.getOriginalFilename());
            multimedia.setType(file.getContentType());
            multimedia.setProduct(product);
            multimediaRepository.save(multimedia);
        }
    }

    @Override
    public ResponseEntity<String> deleteMultimedia(Long id) {
        multimediaRepository.deleteById(id);
        return null;
    }

}
