package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Grade;
import com.holisticbabe.holisticbabemarketplace.Services.GradeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/grade")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @PostMapping("/add/user/{userId}/quiz/{quizId}")
    public ResponseEntity<?> addGrade(
            @PathVariable("userId") long userId,
            @PathVariable("quizId") long quizId,
            @RequestBody Grade grade) {
        try {
            gradeService.addGrade(userId, quizId, grade);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}/course/{courseId}")
    public ResponseEntity<List<Grade>> getByUserAndCourseIds(
            @PathVariable("userId") long userId,
            @PathVariable("courseId") long courseId) {
        try {
            List<Grade> grades = gradeService.getByUserAndCourseIds(userId, courseId);
            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}/subject/{subjectId}")
    public ResponseEntity<List<Grade>> getByUserAndSubjectIds(
            @PathVariable("userId") long userId,
            @PathVariable("subjectId") long subjectId) {
        try {
            List<Grade> grades = gradeService.getByUserAndSubjectIds(userId, subjectId);
            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
