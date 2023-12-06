package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.QuizDto;
import com.holisticbabe.holisticbabemarketplace.Models.Quiz;
import com.holisticbabe.holisticbabemarketplace.Services.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/module/{id}")
    public List<QuizDto> getByModuleId(@PathVariable long id) {
        return quizService.getByModuleId(id);
    }

    @PostMapping
    public ResponseEntity<String> addQuiz(@RequestBody Quiz quiz) {
        try {
            quizService.addQuiz(quiz);
            return ResponseEntity.ok("Quiz added successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateQuiz(@PathVariable long id, @RequestBody Quiz quiz) {
        try {
            quizService.updateQuiz(id, quiz);
            return ResponseEntity.ok("Quiz updated successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuizDto> getByid(@PathVariable long id) {
        try {
            QuizDto quiz = quizService.getByid(id);
            return ResponseEntity.ok(quiz);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteQuiz(@PathVariable long id) {
        try {
            quizService.deleteQuiz(id);
            return ResponseEntity.ok("Quiz deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
