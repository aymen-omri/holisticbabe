package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.QuizDto;
import com.holisticbabe.holisticbabemarketplace.Models.Quiz;

public interface QuizService {
    List<QuizDto> getByModuleId(long id);

    void addQuiz(Quiz quiz);

    void updateQuiz(long id, Quiz quiz);

    QuizDto getByid(long id);

    void deleteQuiz(long id);
}
