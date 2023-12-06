package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select l from Lesson l where l.course.id_course = ?1")
    List<Lesson> getLessonsByCourseId(long id);

}
