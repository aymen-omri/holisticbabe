package com.holisticbabe.holisticbabemarketplace.Dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {
    private long id_lesson;
    private String lessonName;
    private String description;
    private int status;
    private int order;
    private LocalDate createdAt;

    private SubjectDto subject;
}
