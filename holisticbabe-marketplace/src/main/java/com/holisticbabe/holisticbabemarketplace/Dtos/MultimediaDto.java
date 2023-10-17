package com.holisticbabe.holisticbabemarketplace.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultimediaDto {
        private long idMulti;
        private String url;
        private String type;
        private String name;
        private String duration;
}
