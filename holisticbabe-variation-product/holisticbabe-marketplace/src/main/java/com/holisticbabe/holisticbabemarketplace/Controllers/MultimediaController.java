package com.holisticbabe.holisticbabemarketplace.Controllers;


import com.holisticbabe.holisticbabemarketplace.Services.MultimediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
 @RequestMapping("/api/multimedia")
 public class MultimediaController {

  @Autowired
  private MultimediaService multimediaService;

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteMultimedia(@PathVariable Long id) {
        return multimediaService.deleteMultimedia(id);
  }



 }

