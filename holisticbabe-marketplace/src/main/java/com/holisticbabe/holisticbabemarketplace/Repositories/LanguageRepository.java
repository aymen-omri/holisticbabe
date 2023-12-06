package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models.Language;

public interface LanguageRepository extends JpaRepository<Language, Long> {

    Optional<Language> findByLanguageName(String name);

}
