package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.ConsultantDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;

public interface ConsultantService {
    ResponseEntity<?> addConsultant(long id_user, String demoUrl, String demoVideoType, String demoVideoName,
            List<MultipartFile> files, String Description);

    ConsultantDto geConsultantById(long id);

    ConsultantDto getConsultantByUserId(long id);

    List<MultimediaDto> getConsultantFiles(long id);

    ResponseEntity<?> approveConsultant(long id);

    ResponseEntity<?> rejectConsultant(long id);

    List<ConsultantDto> getAll();

    int isApproved(long id);
}
