package com.holisticbabe.holisticbabemarketplace.Utlis;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    
    private final Storage storage;

    public String uploadFile(MultipartFile file , String bucketName) throws IOException {
        //String bucketName = "profile-pictures-holisticbabe";
        String fileName = "user_" + UUID.randomUUID() + "_profile.jpg";
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        // Upload file to Google Cloud Storage
        Blob blob = storage.create(blobInfo, file.getBytes());

        // Get the public URL of the uploaded file
        return blob.getMediaLink();
    }

}
