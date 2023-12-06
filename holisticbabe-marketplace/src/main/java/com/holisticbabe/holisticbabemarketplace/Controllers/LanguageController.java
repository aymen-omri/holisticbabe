package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.LanguageDto;
import com.holisticbabe.holisticbabemarketplace.Models.Language;
import com.holisticbabe.holisticbabemarketplace.Services.LanguageService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/language")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/all")
    public List<LanguageDto> getAllLanguages() {
        return languageService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> getLanguageById(@PathVariable long id) {
        try {
            LanguageDto language = languageService.getById(id);
            return language != null ? ResponseEntity.ok(language) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addLanguage(@RequestBody Language language) {
        try {
            languageService.insertLanguage(language);
            return ResponseEntity.ok("Language added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateLanguage(@PathVariable long id, @RequestBody Language language) {
        return languageService.updateLanguage(id, language);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLanguage(@PathVariable long id) {
        try {
            languageService.deleteLanguage(id);
            return ResponseEntity.ok("Language deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
