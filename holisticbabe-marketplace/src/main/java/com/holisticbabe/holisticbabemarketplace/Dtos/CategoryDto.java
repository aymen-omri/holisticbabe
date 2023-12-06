package com.holisticbabe.holisticbabemarketplace.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private String name;
    private String description;
    private String dateCreated;
}
