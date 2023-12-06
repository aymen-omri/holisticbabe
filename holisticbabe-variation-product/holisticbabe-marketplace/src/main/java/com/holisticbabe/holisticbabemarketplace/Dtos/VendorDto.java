package com.holisticbabe.holisticbabemarketplace.Dtos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class VendorDto {
    private long id_vendor;
    private String companyName;
    private String societyIdentificationNumber;
    private String description;
    private String shopLink;
    private LocalDate date;
    private Boolean approved;
    List<CountryDto> countries = new ArrayList<>();
    private AddressDto companyAddress;
    private UserDto user;
}
