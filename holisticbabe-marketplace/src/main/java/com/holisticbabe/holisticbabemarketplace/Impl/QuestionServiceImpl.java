package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.QuestionDto;
import com.holisticbabe.holisticbabemarketplace.Models.Question;
import com.holisticbabe.holisticbabemarketplace.Repositories.OptionRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.QuestionRepository;
import com.holisticbabe.holisticbabemarketplace.Services.OptionService;
import com.holisticbabe.holisticbabemarketplace.Services.QuestionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    private final OptionService optionService;
    private final ModelMapper modelMapper;

    @Override
    public List<QuestionDto> getQuizQuestions(long id) {
        return this.questionRepository.getQuestionsByQuizId(id).stream().map(question -> modelMapper.map(question , QuestionDto.class)).toList();
    }

    @Override
    public QuestionDto getQuestionById(long id) {
        return modelMapper.map(questionRepository.findById(id).get(), QuestionDto.class);
    }

    @Override
    public QuestionDto addQuestion(Question question) {
        Question savedQuestion = questionRepository.save(question);
        question.getOptions().forEach(option -> {
            option.setQuestion(savedQuestion);
            optionService.addOption(option);
        });
        return modelMapper.map(savedQuestion, QuestionDto.class);
    }

    @Override
    public void deleteQuestion(long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Could not find question with the given ID"));
        List<Question> questions = questionRepository.getQuestionsByQuizId(question.getQuiz().getId_quiz());
        if (questions.size() == 2) {
            throw new IllegalArgumentException("Cannot remove last quiz question");
        }
        optionRepository.deleteAll(question.getOptions());
        questionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateQuestion(long id, Question question) {
        Question questionToUpdate = questionRepository.findById(id).orElse(null);
        if (questionToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        // update the fields that are not empty in the request object
        questionToUpdate.setQuestionText(question.getQuestionText());
        return ResponseEntity.status(200).body("Updated successfully!");
    }

}
