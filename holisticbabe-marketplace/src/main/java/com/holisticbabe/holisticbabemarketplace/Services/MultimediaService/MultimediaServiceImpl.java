package com.holisticbabe.holisticbabemarketplace.Services.MultimediaService;

import com.google.cloud.storage.*;
import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Utils.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class MultimediaServiceImpl implements MultimediaService {

    @Autowired
    private FileService fileService;

    @Autowired
    private MultimediaRepository multimediaRepository;

    @Autowired
    Storage storage;
    @Override
    public void uploadImage(MultipartFile file, Product product) throws IOException {
        // Delegate the file upload to the FileService
        fileService.uploadFile(file);

        // Now, you can handle any additional logic for Multimedia here, like saving metadata
        Multimedia multimedia = new Multimedia();
        multimedia.setUrl("https://storage.googleapis.com/holisticbabebacket/" + file.getOriginalFilename());
        multimedia.setName(file.getOriginalFilename());
        multimedia.setType(file.getContentType());
        multimedia.setProduct(product); // Associate the multimedia with the provided product
        // Set other attributes if needed
        multimediaRepository.save(multimedia);
    }

    @Override
    public ResponseEntity<String> deleteMultimedia(Long id) {
        multimediaRepository.deleteById(id);
        return null;
    }


}
