package com.holisticbabe.holisticbabemarketplace.Utils;

import com.google.cloud.storage.Blob;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    List<String> listOfFiles();
    ByteArrayResource downloadFile(String fileName);
    boolean deleteFile(String fileName);


    String uploadFile(MultipartFile file) throws IOException;
    Blob getBlob(String bucketName);
    void deleteBlob(Blob blob);
}