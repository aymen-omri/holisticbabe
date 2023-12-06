package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Enrollment;

import java.util.List;

public interface EnrollmentService {

    List<Enrollment> getAllByUserId(long id_user);

    Enrollment getById(long id);

    void createEnrollment(long id_user, long id_course);

    void addFinishedLessons(long id_enrollment, long id_lesson);
}
