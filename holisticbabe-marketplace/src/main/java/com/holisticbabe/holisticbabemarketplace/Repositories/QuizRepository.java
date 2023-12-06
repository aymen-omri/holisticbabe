package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    @Query("select q from Quiz q where q.subject.id_subject = ?1")
    List<Quiz> getBySubjectId(long id);
}
