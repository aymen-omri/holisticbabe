package com.holisticbabe.holisticbabemarketplace.Dtos.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountryDto {
    private long id_country;
    private String countryName;
}
