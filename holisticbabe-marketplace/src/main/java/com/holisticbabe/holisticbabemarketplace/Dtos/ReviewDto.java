package com.holisticbabe.holisticbabemarketplace.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private String comment;
    private Integer value;
    private String pros;
    private String cons;
    private UserDto userDto;
    private ProductDto productDto;
}
