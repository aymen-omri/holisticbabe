package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.CourseLevelDto;
import com.holisticbabe.holisticbabemarketplace.Models.CourseLevel;

public interface CourseLevelService {

    List<CourseLevelDto> getAllCourseLevels();

    void save(CourseLevel courselevel);

    CourseLevelDto findById(long id);

    void updateCourseLevel(long id, CourseLevel courseLevel);

    void delete(long id);
}
