package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.ConsultantDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;
import com.holisticbabe.holisticbabemarketplace.Services.ConsultantService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consultant")
@RequiredArgsConstructor
public class ConsultantController {

    private final ConsultantService consultantService;

    @PostMapping("/add/user/{id_user}")
    public ResponseEntity<String> addConsultant(@PathVariable("id_user") long userId,
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("description") String description) {
        return consultantService.addConsultant(userId, files, description);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultantDto> getConsultantById(@PathVariable("id") long id) {
        ConsultantDto consultant = consultantService.geConsultantById(id);
        if (consultant == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(consultant);
    }

    @GetMapping("/user/{id}")
    public ConsultantDto getConsultantByUserId(@PathVariable("id") long id) {
        return consultantService.getConsultantByUserId(id);
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<String> approveConsultant(@PathVariable("id") long id) {
        return consultantService.approveConsultant(id);
    }

    @GetMapping("/all")
    public List<ConsultantDto> getAllConsultants() {
        return consultantService.getAll();
    }

    @GetMapping("/isApproved/{id}")
    public boolean isConsultantApproved(@PathVariable("id") long id) {
        return consultantService.isApproved(id);
    }
    
    @GetMapping("/files/{id}")
    public ResponseEntity<List<MultimediaDto>> getConsultantFiles(@PathVariable long id) {
        return ResponseEntity.ok(consultantService.getConsultantFiles(id));
    }
}
