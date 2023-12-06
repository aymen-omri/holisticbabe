package com.holisticbabe.holisticbabemarketplace.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryWithImageDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;

}
