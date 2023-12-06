package com.holisticbabe.holisticbabemarketplace.Dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id_comment;
    private String comment;
    private LocalDate dateOfComment;

    private CommentDto parentComment;

    private UserDto user;

    private CourseDto course;
}
