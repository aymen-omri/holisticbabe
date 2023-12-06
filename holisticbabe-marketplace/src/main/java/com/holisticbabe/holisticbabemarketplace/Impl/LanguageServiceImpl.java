package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.LanguageDto;
import com.holisticbabe.holisticbabemarketplace.Models.Language;
import com.holisticbabe.holisticbabemarketplace.Repositories.LanguageRepository;
import com.holisticbabe.holisticbabemarketplace.Services.LanguageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<LanguageDto> getAll() {
        return languageRepository
                .findAll()
                .stream()
                .sorted(Comparator.comparing(Language::getLanguageName))
                .map(lang -> modelMapper.map(lang, LanguageDto.class))
                .toList();

    }

    @Override
    public LanguageDto getById(long id) {
        return modelMapper.map(languageRepository.findById(id).get(), LanguageDto.class);
    }

    @Override
    public void insertLanguage(Language language) {
        Optional<Language> lang = languageRepository.findByLanguageName(language.getLanguageName());
        if (lang.isPresent()) {
            throw new IllegalArgumentException("This language already exists");
        }
        languageRepository.save(language);
    }

    @Override
    public void deleteLanguage(long id) {
        languageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateLanguage(long id, Language language) {
        return languageRepository.findByLanguageName(language.getLanguageName())
                .filter(lang -> lang.getId_language() != id)
                .map(lang -> ResponseEntity.badRequest().body("Language name already exists"))
                .orElseGet(() -> languageRepository.findById(id)
                        .map(langToUpdate -> {
                            langToUpdate.setLanguageName(language.getLanguageName());
                            return ResponseEntity.ok("Language updated successfully!");
                        })
                        .orElse(ResponseEntity.status(404).body("Language not found!")));
    }

}
