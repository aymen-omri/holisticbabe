package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Grade;

import java.util.List;

public interface GradeService {
    void addGrade(long id_user , long id_quiz , Grade grade );

    List<Grade> getByUserAndCourseIds(long user , long id_course);

    List<Grade> getByUserAndSubjectIds(long id_user , long id_subject);
}
