package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.QuestionDto;
import com.holisticbabe.holisticbabemarketplace.Models.Question;
import com.holisticbabe.holisticbabemarketplace.Services.QuestionService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/course/{id}")
    public List<QuestionDto> getCourseQuestions(@PathVariable long id) {
        return questionService.getQuizQuestions(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable long id) {
        try {
            QuestionDto question = questionService.getQuestionById(id);
            return ResponseEntity.ok(question);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody Question question) {
        try {
            QuestionDto addedQuestion = questionService.addQuestion(question);
            return ResponseEntity.status(201).body(addedQuestion);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.ok("Question deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable long id, @RequestBody Question question) {
        ResponseEntity<String> response = questionService.updateQuestion(id, question);
        if (response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok("Question updated successfully!");
        } else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }
}
