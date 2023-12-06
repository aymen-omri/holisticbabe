package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Dtos.QuestionDto;
import com.holisticbabe.holisticbabemarketplace.Models.Question;

public interface QuestionService {
    List<QuestionDto> getQuizQuestions(long id);

    QuestionDto getQuestionById(long id);

    QuestionDto addQuestion(Question question);

    void deleteQuestion(long id_question);

    ResponseEntity<String> updateQuestion(long id, Question question);
}
