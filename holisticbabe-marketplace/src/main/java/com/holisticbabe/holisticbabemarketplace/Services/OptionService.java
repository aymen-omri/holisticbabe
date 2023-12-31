package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Models._Option;

public interface OptionService {
    List<_Option> getQuestionOptions(long id);

    _Option getOptionById(long id);

    _Option addOption(_Option option);

    void deleteOption(long id);

    ResponseEntity<String> updateOption(long id, _Option option);
}
