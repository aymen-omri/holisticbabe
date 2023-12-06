package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.holisticbabe.holisticbabemarketplace.Models.Question;
import com.holisticbabe.holisticbabemarketplace.Repositories.QuestionRepository;
import com.holisticbabe.holisticbabemarketplace.Services.QuestionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Override
    public List<Question> getCourseQuestions(long id) {
        return this.questionRepository.getQuestionsByCourseId(id);
    }

    @Override
    public Question getQuestionById(long id) {
        return this.questionRepository.findById(id).orElse(null);
    }

    @Override
    public Question addQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(long id) {
        this.questionRepository.deleteById(id);
    }

    @Override
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
