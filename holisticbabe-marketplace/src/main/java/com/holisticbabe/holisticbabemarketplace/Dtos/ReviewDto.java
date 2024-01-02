package com.holisticbabe.holisticbabemarketplace.Dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewDto {
    private Long id;
    private String comment;
    private Integer value;
    private  String image;
    private Long userId;
    private Long productId;
    private String userName;
    private LocalDate dateCreated;

}
