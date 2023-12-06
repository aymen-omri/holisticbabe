package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.CourseDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;
import com.holisticbabe.holisticbabemarketplace.Models.Course;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;

public interface CourseService {
    List<CourseDto> getAll();

    List<CourseDto> getUserCourses(long id);

    CourseDto getById(long id);

    void addCourse(Course course);

    void addCourseMultimedia(Multimedia multimedia);

    void updateCourseMultimedia(long id, Multimedia multimedia);

    void updateCourse(long id_course, long id_language, long id_course_level, long id_category, Course course);

    List<MultimediaDto> getCourseMultimediaContent(long id);

    void deleteCourse(long id);

    void approveCourse(long id);

    void rejectCourse(long id);

    void submitCourse(long id);
}
