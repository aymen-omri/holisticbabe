package com.holisticbabe.holisticbabemarketplace.Repositories;


import com.holisticbabe.holisticbabemarketplace.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
