package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Models.Grade;
import com.holisticbabe.holisticbabemarketplace.Models.Quiz;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.GradeRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.QuizRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Services.GradeService;

import lombok.RequiredArgsConstructor;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    @Override
    public void addGrade(long id_user, long id_quiz, Grade grade) {
        _User user = userRepository.findById(id_user).orElseThrow(
                () -> new RuntimeException("user not found"));
        Quiz quiz = quizRepository.findById(id_quiz).orElseThrow(
                () -> new RuntimeException("Quiz not found"));
        grade.setGradeDate(LocalDate.now());
        grade.setQuiz(quiz);
        grade.setUser(user);
        gradeRepository.save(grade);
    }

    @Override
    public List<Grade> getByUserAndCourseIds(long user, long id_course) {
        return gradeRepository.findByUserAndCourse(user, id_course);
    }

    @Override
    public List<Grade> getByUserAndSubjectIds(long id_user, long id_subject) {
        return gradeRepository.findByUserAndSubject(id_user, id_subject);
    }

}
