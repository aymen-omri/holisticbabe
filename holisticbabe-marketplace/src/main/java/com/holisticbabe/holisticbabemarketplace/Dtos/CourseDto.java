package com.holisticbabe.holisticbabemarketplace.Dtos;

import org.threeten.bp.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDto {
    private long idCourse;
    private String title;
    private String subtitle;
    private String requirements;
    private String description;
    private String target;
    private int status;
    private LocalDate createdAt;
    private LocalDate deletedAt;
    private LocalDate updatedAt;

    private LanguageDto language;
    private CourseLevelDto courseLevel;
    private CategoryDto category;
    private UserDto instructor;
}
