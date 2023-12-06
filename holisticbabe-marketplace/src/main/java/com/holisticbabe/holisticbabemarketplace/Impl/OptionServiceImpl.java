package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.OptionDto;
import com.holisticbabe.holisticbabemarketplace.Models._Option;
import com.holisticbabe.holisticbabemarketplace.Repositories.OptionRepository;
import com.holisticbabe.holisticbabemarketplace.Services.OptionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<OptionDto> getQuestionOptions(long id) {
        return optionRepository.getOptionsByQuestionId(id).stream()
                .map(option -> modelMapper.map(option, OptionDto.class)).toList();
    }

    @Override
    public OptionDto getOptionById(long id) {
        return modelMapper.map(optionRepository.findById(id).get(), OptionDto.class);
    }

    @Override
    public OptionDto addOption(_Option option) {
        return modelMapper.map(optionRepository.save(option), OptionDto.class);
    }

    @Override
    public void deleteOption(long id) {
        _Option option = optionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find option with the given ID"));
        List<_Option> options = optionRepository.getOptionsByQuestionId(option.getQuestion().getId_question());
        if (options.size() == 2) {
            throw new RuntimeException("You cannot remove all options");
        }
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
        optionToUpdate.setExplanation(option.getExplanation());
        return ResponseEntity.status(200).body("Updated successfully!");

    }

}
