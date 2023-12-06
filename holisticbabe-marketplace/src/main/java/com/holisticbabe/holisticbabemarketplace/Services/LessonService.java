package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.LessonDto;
import com.holisticbabe.holisticbabemarketplace.Models.Lesson;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;

public interface LessonService {
    List<LessonDto> getModuleLessons(long id);

    LessonDto getLessonById(long id);

    LessonDto addLesson(Lesson lesson, long id_module);

    void deleteLesson(long id);

    void updateLesson(long id, Lesson lesson);

    void addLessonMultimediaContent(long id , Multimedia multimedia);
}
