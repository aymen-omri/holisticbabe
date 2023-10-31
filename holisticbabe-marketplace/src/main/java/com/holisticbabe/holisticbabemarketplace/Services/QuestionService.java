package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Models.Question;

public interface QuestionService {
    List<Question> getCourseQuestions(long id);

    Question getQuestionById(long id);

    Question addQuestion(Question question);

    void deleteQuestion(long id);

    ResponseEntity<String> updateQuestion(long id, Question question);
}
