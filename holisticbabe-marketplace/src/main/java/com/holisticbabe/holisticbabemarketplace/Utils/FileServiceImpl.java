package com.holisticbabe.holisticbabemarketplace.Utils;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Autowired
    Storage storage;


    @Autowired
    private MultimediaRepository multimediaRepository;

    @Override
    public List<String> listOfFiles() {

        List<String> list = new ArrayList<>();
        Page<Blob> blobs = storage.list(bucketName);
        for (Blob blob : blobs.iterateAll()) {
            list.add(blob.getName());
        }
        return list;
    }

    @Override
    public ByteArrayResource downloadFile(String fileName) {

        Blob blob = storage.get(bucketName, fileName);
        ByteArrayResource resource = new ByteArrayResource(
                blob.getContent());

        return resource;
    }

    @Override
    public boolean deleteFile(String fileName) {

        Blob blob = storage.get(bucketName, fileName);

        return blob.delete();
    }


    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        BlobId blobId = BlobId.of(bucketName, file.getOriginalFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).
                setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo,file.getBytes());
        return null;
    }


    public Blob getBlob(String fileUrl) {
        try {
            if (fileUrl != null) {
                // Extract the bucket name and object name from the file URL
                String[] parts = fileUrl.split("/");
                String bucketName = parts[parts.length - 2];
                String objectName = parts[parts.length - 1];

                // Get the Blob
                return storage.get(bucketName, objectName);
            }
        } catch (Exception e) {
            // Handle any exceptions, log the error, or throw a custom exception
        }
        return null;
    }

    public void deleteBlob(Blob blob) {
        try {
            if (blob != null) {
                // Delete the Blob from Cloud Storage
                blob.delete();
            }
        } catch (Exception e) {
            // Handle any exceptions, log the error, or throw a custom exception
        }
    }

}