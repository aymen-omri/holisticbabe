package com.holisticbabe.holisticbabemarketplace.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {

    private long id_option;
    private String optionText;
    private int isCorrect;
    private String explanation;

}
