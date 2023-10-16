package com.holisticbabe.holisticbabemarketplace.Dtos.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private long id_address;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String postalCode;
    private UserDto user;
    private CountryDto country;
}
