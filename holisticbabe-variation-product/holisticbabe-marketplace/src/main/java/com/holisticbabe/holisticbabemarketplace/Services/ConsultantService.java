package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.ConsultantDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;

public interface ConsultantService {
    ResponseEntity<String> addConsultant(long id_user, List<MultipartFile> files, String Description);

    ConsultantDto geConsultantById(long id);

    ConsultantDto getConsultantByUserId(long id);

    List<MultimediaDto> getConsultantFiles(long id);

    ResponseEntity<String> approveConsultant(long id);

    List<ConsultantDto> getAll();

    boolean isApproved(long id);
}
