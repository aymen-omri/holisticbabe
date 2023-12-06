package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;
import com.holisticbabe.holisticbabemarketplace.Services.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/multimedia")
public class MultimediaController {

      @Autowired
      private MultimediaService multimediaService;

      @DeleteMapping("/{id}")
      public ResponseEntity<String> deleteMultimedia(@PathVariable Long id) {
            return multimediaService.deleteMultimedia(id);
      }

      @PostMapping("/consultant-demo-video")
      public ResponseEntity<MultimediaDto> uploadDemoVideoForConsultant(@RequestParam("file") MultipartFile video) {
            MultimediaDto uploadedVideo = multimediaService.uploadDemoVideoForConsultant(video);
            if (uploadedVideo != null) {
                  return ResponseEntity.ok(uploadedVideo);
            }
            return ResponseEntity.badRequest().build();
      }

}
