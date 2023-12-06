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

import com.holisticbabe.holisticbabemarketplace.Dtos.LessonDto;
import com.holisticbabe.holisticbabemarketplace.Models.Lesson;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Services.LessonService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/subject/{id}")
    public ResponseEntity<List<LessonDto>> getModuleLessons(@PathVariable long id) {
        List<LessonDto> lessons = lessonService.getModuleLessons(id);
        return new ResponseEntity<>(lessons, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable long id) {
        try {
            LessonDto lesson = lessonService.getLessonById(id);
            if (lesson != null) {
                return new ResponseEntity<>(lesson, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add/{id_subject}")
    public ResponseEntity<LessonDto> addLesson(@RequestBody Lesson lesson, @PathVariable long id_subject) {
        try {
            LessonDto addedLesson = lessonService.addLesson(lesson, id_subject);
            return new ResponseEntity<>(addedLesson, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable long id) {
        try {
            lessonService.deleteLesson(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateLesson(@PathVariable long id, @RequestBody Lesson lesson) {
        try {
            lessonService.updateLesson(id, lesson);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMultimedia/{id}")
    public ResponseEntity<Void> addLessonMultimediaContent(@PathVariable long id, @RequestBody Multimedia multimedia) {
        try {
            lessonService.addLessonMultimediaContent(id, multimedia);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
