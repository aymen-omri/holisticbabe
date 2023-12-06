package com.holisticbabe.holisticbabemarketplace.Dtos;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDto {
    private long id_quiz;
    private String name;
    private String description;
    private double scoreToPass;
    private int status;
    private LocalDate createdAt;

    private SubjectDto subject;

    private List<QuestionDto> questions;
}
