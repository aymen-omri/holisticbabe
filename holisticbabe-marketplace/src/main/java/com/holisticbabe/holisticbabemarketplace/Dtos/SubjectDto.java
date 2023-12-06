package com.holisticbabe.holisticbabemarketplace.Dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDto {
    private long id_subject;
    private String name;
    private String description;
    private int status;
    private int order;
    private LocalDate createdAt;

    private CourseDto course;

}
