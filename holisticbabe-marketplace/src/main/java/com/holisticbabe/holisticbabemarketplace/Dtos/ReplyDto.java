package com.holisticbabe.holisticbabemarketplace.Dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReplyDto {
    private Long id;
    private Long reviewId;
    private Long userId;
    private String content;
    private LocalDate dateCreated;
    private String userName;
    private String image;


}
