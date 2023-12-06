package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.holisticbabe.holisticbabemarketplace.Models.Language;
import com.holisticbabe.holisticbabemarketplace.Repositories.LanguageRepository;
import com.holisticbabe.holisticbabemarketplace.Services.LanguageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Override
    public List<Language> getAll() {
        return languageRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Language::getLanguageName))
                .toList();

    }

    @Override
    public Language getById(long id) {
        return languageRepository.findById(id).orElse(null);
    }

    @Override
    public void insertLanguage(Language language) {
        languageRepository.save(language);
    }

    @Override
    public void deleteLanguage(long id) {
        languageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateLanguage(long id, Language language) {
        Optional<Language> langToUpdate = languageRepository.findById(id);
        if (langToUpdate.isPresent()) {
            langToUpdate.get().setLanguageName(language.getLanguageName());
            return ResponseEntity.ok("Language updated successfully!");
        }
        return ResponseEntity.status(404).body("Language not found!");
    }

}
