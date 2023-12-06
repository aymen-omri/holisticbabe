package com.holisticbabe.holisticbabemarketplace.Impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.ConsultantDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;
import com.holisticbabe.holisticbabemarketplace.Models.Consultant;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.ConsultantRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Services.ConsultantService;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultantServiceImpl implements ConsultantService {

    private final MultimediaRepository multimediaRepository;
    private final UserRepository userRepository;
    private final ConsultantRepository consultantRepository;
    private final FileUploadService fileUploadService;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<String> addConsultant(long id_user, List<MultipartFile> files, String Description) {
        _User user = userRepository.findById(id_user).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Consultant consultant = new Consultant();
        consultant.setUser(user);
        consultant.setDescription(Description);
        consultant.setApproved(false);
        consultant.setDate(LocalDate.now());
        Consultant savedConsultant = consultantRepository.save(consultant);
        for (MultipartFile file : files) {
            try {
                String link = fileUploadService.uploadFile(file, "consultants-files");
                Multimedia fileToSave = new Multimedia();
                fileToSave.setUrl(link);
                fileToSave.setName(file.getOriginalFilename());
                fileToSave.setType(file.getContentType());
                fileToSave.setConsultant(savedConsultant);
                multimediaRepository.save(fileToSave);
            } catch (IOException e) {
                consultantRepository.deleteById(savedConsultant.getId_consultant());
                return ResponseEntity.status(500).body("Error while inserting consultant files!");
            }
        }

        return ResponseEntity.ok("Consultant inserted successfully!");

    }

    @Override
    public ConsultantDto geConsultantById(long id) {
        Optional<Consultant> optConsultant = consultantRepository.findById(id);
        if (!optConsultant.isPresent()) {
            return null;
        }
        return modelMapper.map(optConsultant.get(), ConsultantDto.class);

    }

    @Override
    public ConsultantDto getConsultantByUserId(long id) {
        return modelMapper.map(consultantRepository.getConsultantByUserId(id), ConsultantDto.class);
    }

    @Override
    @Transactional
    public ResponseEntity<String> approveConsultant(long id) {
        Optional<Consultant> consultant = consultantRepository.findById(id);
        if (!consultant.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            consultant.get().setApproved(true);
            return ResponseEntity.ok("Consultant approved successfully!");
        }
    }

    @Override
    public List<ConsultantDto> getAll() {
        return consultantRepository.findAll().stream().map(c -> modelMapper.map(c, ConsultantDto.class)).toList();
    }

    @Override
    public boolean isApproved(long id) {
        Consultant consultant = consultantRepository.findById(id).orElse(null);
        if (consultant == null) {
            return false;
        }
        return consultant.isApproved();
    }

    @Override
    public List<MultimediaDto> getConsultantFiles(long id) {
        return multimediaRepository.getFilesByConsultantId(id).stream()
                .map(file -> modelMapper.map(file, MultimediaDto.class)).toList();
    }

}
