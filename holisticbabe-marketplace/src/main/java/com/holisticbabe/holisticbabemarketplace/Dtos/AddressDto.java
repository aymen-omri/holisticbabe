package com.holisticbabe.holisticbabemarketplace.Dtos;

import com.holisticbabe.holisticbabemarketplace.Models.AddressType;

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
    private AddressType addressType;
    private UserDto user;
    private CountryDto country;
}
