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
import com.holisticbabe.holisticbabemarketplace.Requests.SuccessMessageRequest;
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
    public ResponseEntity<?> addConsultant(long id_user, String demoUrl, String demoVideoType,
            String demoVideoName, List<MultipartFile> files, String Description) {
        _User user = userRepository.findById(id_user).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Optional<Consultant> existingConsultant = consultantRepository.getConsultantByUserId(user.getId_user());
        if (existingConsultant.isPresent()) {
            var con = existingConsultant.get();
            if (con.getApproved() == 0) {
                return ResponseEntity.badRequest().body("You already sent a request");
            } else if (con.getApproved() == 1) {
                return ResponseEntity.badRequest().body("Your request is approved");
            } else {
                multimediaRepository.deleteAll(multimediaRepository.getFilesByConsultantId(con.getId_consultant()));
                consultantRepository.deleteById(con.getId_consultant());
            }
        }
        Consultant consultant = new Consultant();
        consultant.setUser(user);
        consultant.setDescription(Description);
        consultant.setApproved(0);
        consultant.setDate(LocalDate.now());
        Consultant savedConsultant = consultantRepository.save(consultant);
        Multimedia demo = new Multimedia();
        demo.setUrl(demoUrl);
        demo.setName(demoVideoName);
        demo.setType(demoVideoType);
        demo.setConsultant(savedConsultant);
        Multimedia savedDemo = multimediaRepository.save(demo);
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
                multimediaRepository.deleteById(savedDemo.getId_multi());
                consultantRepository.deleteById(savedConsultant.getId_consultant());
                return ResponseEntity.status(500).body("Error while inserting consultant files!");
            }
        }

        return ResponseEntity.ok(new SuccessMessageRequest(200, "Consultant inserted successfully!"));

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
    public ResponseEntity<?> approveConsultant(long id) {
        Optional<Consultant> consultant = consultantRepository.findById(id);
        if (!consultant.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            consultant.get().setApproved(1);
            return ResponseEntity.ok(new SuccessMessageRequest(200, "Consultant approved successfully!"));
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> rejectConsultant(long id) {
        Optional<Consultant> consultant = consultantRepository.findById(id);
        if (!consultant.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            consultant.get().setApproved(2);
            return ResponseEntity.ok(new SuccessMessageRequest(200, "Consultant rejected!"));
        }
    }

    @Override
    public List<ConsultantDto> getAll() {
        return consultantRepository.findAll().stream().map(c -> modelMapper.map(c, ConsultantDto.class)).toList();
    }

    @Override
    public int isApproved(long id) {
        Consultant consultant = consultantRepository.findById(id).orElse(null);
        if (consultant == null) {
            return 2;
        }
        return consultant.getApproved();
    }

    @Override
    public List<MultimediaDto> getConsultantFiles(long id) {
        return multimediaRepository.getFilesByConsultantId(id).stream()
                .map(file -> modelMapper.map(file, MultimediaDto.class)).toList();
    }

}
