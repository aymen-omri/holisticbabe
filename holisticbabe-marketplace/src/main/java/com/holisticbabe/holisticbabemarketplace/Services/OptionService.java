package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Dtos.OptionDto;
import com.holisticbabe.holisticbabemarketplace.Models._Option;

public interface OptionService {
    List<OptionDto> getQuestionOptions(long id);

    OptionDto getOptionById(long id);

    OptionDto addOption(_Option option);

    void deleteOption(long id);

    ResponseEntity<String> updateOption(long id, _Option option);
}
