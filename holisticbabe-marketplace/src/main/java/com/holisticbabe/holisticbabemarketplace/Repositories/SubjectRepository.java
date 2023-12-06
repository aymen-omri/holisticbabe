package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models._Subject;

public interface SubjectRepository extends JpaRepository<_Subject, Long> {

    @Query("select s from _Subject s where s.course.id_course=?1")
    List<_Subject> findByCourseId(long id);

}
