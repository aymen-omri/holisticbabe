package com.holisticbabe.holisticbabemarketplace.Controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.holisticbabe.holisticbabemarketplace.Dtos.SubjectDto;
import com.holisticbabe.holisticbabemarketplace.Models._Subject;
import com.holisticbabe.holisticbabemarketplace.Services.SubjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping("/course/{id}")
    public ResponseEntity<List<SubjectDto>> getSubjectsByCourseId(@PathVariable long id) {
            List<SubjectDto> subjects = subjectService.getSubjectsByCourseId(id);
            return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable long id) {
        try {
            SubjectDto subject = subjectService.getById(id);
            if (subject != null) {
                return new ResponseEntity<>(subject, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add/{id_course}")
    public ResponseEntity<SubjectDto> addSubject(@RequestBody _Subject subject, @PathVariable long id_course) {
        try {
            SubjectDto addedSubject = subjectService.addSubject(id_course, subject);
            return new ResponseEntity<>(addedSubject, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateSubject(@PathVariable long id, @RequestBody _Subject subject) {
        try {
            subjectService.updateSubject(id, subject);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable long id) {
        try {
            subjectService.deleteSubject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
