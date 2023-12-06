package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.holisticbabe.holisticbabemarketplace.Models.CourseLevel;

public interface CourseLevelRepository extends JpaRepository<CourseLevel, Long> {
    Optional<CourseLevel> findByCourseLevel(String name);
}
