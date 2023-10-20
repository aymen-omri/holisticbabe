package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Models.Option;

public interface OptionService {
    List<Option> getQuestionOptions(long id);

    Option getOptionById(long id);

    Option addOption(Option option);

    void deleteOption(long id);

    ResponseEntity<String> updateOption(long id, Option option);
}
