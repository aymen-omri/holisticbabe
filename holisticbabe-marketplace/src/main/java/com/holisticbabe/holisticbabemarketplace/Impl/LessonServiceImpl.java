package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Models.Lesson;
import com.holisticbabe.holisticbabemarketplace.Services.LessonService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    //private final LessonRepository lessonRepository ; 

    @Override
    public List<Lesson> getCourseLessons(long id) {
        return null ;
    }

    @Override
    public Lesson getLessonById(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLessonById'");
    }

    @Override
    public Lesson addLesson(Lesson lesson) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addLesson'");
    }

    @Override
    public void deleteLesson(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteLesson'");
    }

    @Override
    public ResponseEntity<String> updateLesson(long id, Lesson lesson) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateLesson'");
    }
    
}
