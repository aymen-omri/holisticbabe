package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

import com.holisticbabe.holisticbabemarketplace.Models.Course;
import com.holisticbabe.holisticbabemarketplace.Models.Enrollment;
import com.holisticbabe.holisticbabemarketplace.Models.Lesson;
import com.holisticbabe.holisticbabemarketplace.Models.LessonProgress;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.CourseRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.EnrollementRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.LessonProgressRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.LessonRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Services.EnrollmentService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final LessonProgressRepository lessonProgressRepository;
    private final EnrollementRepository enrollementRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;

    @Override
    public List<Enrollment> getAllByUserId(long id_user) {
        return enrollementRepository.findByUserId(id_user);
    }

    @Override
    public Enrollment getById(long id) {
        return enrollementRepository.findById(id).orElse(null);
    }

    @Override
    public void createEnrollment(long id_user, long id_course) {
        Course course = courseRepository.findById(id_course).orElseThrow(
                () -> new RuntimeException("Course not found"));
        _User user = userRepository.findById(id_user).orElseThrow(
                () -> new RuntimeException("user not found"));
        Enrollment newEnrollment = new Enrollment();
        newEnrollment.setEnrollmentDate(LocalDate.now());
        newEnrollment.setUser(user);
        newEnrollment.setCourse(course);
        enrollementRepository.save(newEnrollment);
    }

    @Override
    @Transactional
    public void addFinishedLessons(long id_enrollment, long id_lesson) {
        Lesson lesson = lessonRepository.findById(id_lesson).orElseThrow(
                () -> new RuntimeException("Lesson not found"));

        Enrollment enrollment = enrollementRepository.findById(id_enrollment).orElseThrow(
                () -> new RuntimeException("Enrollment not not found"));

        LessonProgress lessonProgress = new LessonProgress();
        lessonProgress.setLesson(lesson);
        lessonProgress.setFinished(true);
        lessonProgressRepository.save(lessonProgress);

        enrollment.getLessonsProgress().add(lessonProgress);

    }

}
