package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Models.Lesson;

public interface LessonService {
    List<Lesson> getCourseLessons(long id);

    Lesson getLessonById(long id);

    Lesson addLesson(Lesson lesson);

    void deleteLesson(long id);

    ResponseEntity<String> updateLesson(long id, Lesson lesson);
}
