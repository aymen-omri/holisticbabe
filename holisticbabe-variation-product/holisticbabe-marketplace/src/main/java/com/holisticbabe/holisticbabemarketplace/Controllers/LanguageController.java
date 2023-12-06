package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Language;
import com.holisticbabe.holisticbabemarketplace.Services.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/language")
public class LanguageController {

    private final LanguageService languageService;

    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/all")
    public List<Language> getAllLanguages() {
        return languageService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable long id) {
        Language language = languageService.getById(id);
        return language != null ? ResponseEntity.ok(language) : ResponseEntity.notFound().build();
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
