package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Repositories.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Services.MultimediaService;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;

import lombok.RequiredArgsConstructor;

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
    public void uploadImage(MultipartFile file, Product product) throws IOException {
        // Delegate the file upload to the FileService
        // fileService.uploadFile(file);

        // Now, you can handle any additional logic for Multimedia here, like saving
        // metadata
        Multimedia multimedia = new Multimedia();
        String url = fileUploadService.uploadFile(file, "product-multimedia");
        multimedia.setUrl(url);
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

    @Override
    public MultimediaDto uploadDemoVideoForConsultant(MultipartFile video) {
        String url;
        try {
            url = fileUploadService.uploadFile(video, "consultants-files");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        MultimediaDto uploadedVideo = new MultimediaDto();
        uploadedVideo.setName(video.getOriginalFilename());
        uploadedVideo.setUrl(url);
        uploadedVideo.setType(video.getContentType());
        return uploadedVideo;
    }

}
