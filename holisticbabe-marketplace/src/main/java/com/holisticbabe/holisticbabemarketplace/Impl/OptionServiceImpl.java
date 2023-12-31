package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Models._Option;
import com.holisticbabe.holisticbabemarketplace.Repositories.OptionRepository;
import com.holisticbabe.holisticbabemarketplace.Services.OptionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;

    @Override
    public List<_Option> getQuestionOptions(long id) {
        return optionRepository.getOptionsByQuestionId(id);
    }

    @Override
    public _Option getOptionById(long id) {
        return optionRepository.findById(id).orElse(null);
    }

    @Override
    public _Option addOption(_Option option) {
        return optionRepository.save(option);
    }

    @Override
    public void deleteOption(long id) {
        optionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateOption(long id, _Option option) {
        _Option optionToUpdate = optionRepository.findById(id).orElse(null);
        if (optionToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        // update the fields that are not empty in the request object
        optionToUpdate.setOptionText(option.getOptionText());
        optionToUpdate.setIsCorrect(option.getIsCorrect());
        return ResponseEntity.status(200).body("Updated successfully!");

    }

}
