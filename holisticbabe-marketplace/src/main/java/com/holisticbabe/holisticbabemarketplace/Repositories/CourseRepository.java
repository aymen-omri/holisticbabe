package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select c from Course c where c.instructor.id_user=?1")
    List<Course> findCoursesByIdUser(long id);
}
