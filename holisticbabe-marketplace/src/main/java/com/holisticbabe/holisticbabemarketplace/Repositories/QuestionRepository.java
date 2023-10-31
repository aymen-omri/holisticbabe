package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("select q from Question q where q.course.id_course = ?1")
    List<Question> getQuestionsByCourseId(long id);
}
