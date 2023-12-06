package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Models.Language;

public interface LanguageService {
    List<Language> getAll();

    Language getById(long id);

    void insertLanguage(Language language);

    void deleteLanguage(long id);

    ResponseEntity<String> updateLanguage(long id, Language language);
}
