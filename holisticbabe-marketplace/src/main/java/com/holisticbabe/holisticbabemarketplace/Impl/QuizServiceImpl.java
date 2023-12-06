package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.QuizDto;
import com.holisticbabe.holisticbabemarketplace.Models.Quiz;
import com.holisticbabe.holisticbabemarketplace.Repositories.QuizRepository;
import com.holisticbabe.holisticbabemarketplace.Services.QuestionService;
import com.holisticbabe.holisticbabemarketplace.Services.QuizService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuestionService questionService;
    private final ModelMapper modelMapper;

    @Override
    public List<QuizDto> getByModuleId(long id) {
        return quizRepository.findAll().stream().map(quiz -> modelMapper.map(quiz, QuizDto.class)).toList();
    }

    @Override
    public void addQuiz(Quiz quiz) {
        if (quiz.getQuestions().size() < 2) {
            throw new IllegalArgumentException("A Quiz must have at least 2 questions");
        } else {
            quiz.getQuestions().forEach(question -> {
                if (question.getOptions().size() < 2) {
                    throw new IllegalArgumentException("Each question in a Quiz must have at least two options");
                }
            });
        }
        Quiz savedQuiz = quizRepository.save(quiz);
        quiz.getQuestions().forEach(question -> {
            question.setQuiz(savedQuiz);
            questionService.addQuestion(question);
        });
    }

    @Override
    public void updateQuiz(long id, Quiz quiz) {
        Optional<Quiz> quizToUpdate = quizRepository.findById(id);
        if (quizToUpdate.isPresent()) {
            Quiz updatedQuiz = quizToUpdate.get();
            updatedQuiz.setName(quiz.getName());
            updatedQuiz.setDescription(quiz.getDescription());
            updatedQuiz.setScoreToPass(quiz.getScoreToPass());
            quizRepository.saveAndFlush(updatedQuiz);
        } else {
            throw new RuntimeException("Quiz not found");
        }
    }

    @Override
    public QuizDto getByid(long id) {
        return modelMapper.map(quizRepository.findById(id).get(), QuizDto.class);
    }

    @Override
    @Transactional
    public void deleteQuiz(long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (!quiz.isPresent()) {
            throw new RuntimeException("Quiz not found");
        }
        quiz.get().setStatus(0);
    }

}
