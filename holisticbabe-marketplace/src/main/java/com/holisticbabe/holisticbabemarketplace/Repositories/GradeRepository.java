package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("select g from Grade g where g.user.id_user = ?1 and g.quiz.subject.course.id_course = ?2")
    List<Grade> findByUserAndCourse(long id_user, long id_course);

    @Query("select g from Grade g where g.user.id_user = ?1 and g.quiz.subject.id_subject = ?2")
    List<Grade> findByUserAndSubject(long id_user, long id_subject);
}
