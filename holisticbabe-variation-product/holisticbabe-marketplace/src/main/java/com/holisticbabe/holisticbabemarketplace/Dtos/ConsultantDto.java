package com.holisticbabe.holisticbabemarketplace.Dtos;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ConsultantDto {
    private Long id_consultant;
    private String description;
    private LocalDate date;
    private UserDto user;
}
