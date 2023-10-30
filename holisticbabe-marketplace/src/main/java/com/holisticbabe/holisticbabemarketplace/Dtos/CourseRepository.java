package com.holisticbabe.holisticbabemarketplace.Dtos;


import com.holisticbabe.holisticbabemarketplace.Models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
