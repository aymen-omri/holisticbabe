package com.holisticbabe.holisticbabemarketplace.Requests;

import java.util.ArrayList;
import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.AddressDto;

import lombok.Data;

@Data
public class VendorInsertRequest {
    private long id_vendor;
    private String companyName;
    private String societyIdentificationNumber;
    private AddressDto address;
    List<String> countries = new ArrayList<>();
    private String description;
    private String shopLink;
}
