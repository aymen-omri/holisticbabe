package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.SocialMediaDto;
import com.holisticbabe.holisticbabemarketplace.Models.SocialMedia;
import com.holisticbabe.holisticbabemarketplace.Requests.SuccessMessageRequest;
import com.holisticbabe.holisticbabemarketplace.Services.SocialMediaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/social-media")
@RequiredArgsConstructor
public class SocialMediaController {

    private final SocialMediaService socialMediaService;

    @GetMapping("/{id}")
    public ResponseEntity<SocialMediaDto> getById(@PathVariable long id) {
        SocialMediaDto socialMediaDto = socialMediaService.getById(id);
        return socialMediaDto != null ? ResponseEntity.ok(socialMediaDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<SocialMediaDto> getByUserId(@PathVariable long id) {
        SocialMediaDto socialMediaDto = socialMediaService.getByUserId(id);
        return socialMediaDto != null ? ResponseEntity.ok(socialMediaDto) : ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@RequestBody SocialMedia socialMedia) {
        try {
            socialMediaService.save(socialMedia);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessMessageRequest(200 , "Saved successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving: " + e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody SocialMediaDto socialMediaDto, @PathVariable long id) {
        ResponseEntity<?> response = socialMediaService.update(socialMediaDto, id);
        return response != null ? response : ResponseEntity.notFound().build();
    }
}

