package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.VendorDto;

public interface VendorService {

    VendorDto getVendorById(long id);

    VendorDto getVendorByUserId(long id); 

    ResponseEntity<String> saveVendor(
            String addressLine1,
            String addressLine2,
            String city,
            String postalCode,
            long id_country,
            String companyName,
            String identificationNumber,
            String vendorDescription,
            String shopLink,
            long id_user,
            List<String> countries,
            List<MultipartFile> files);

    // ResponseEntity<String> updateVendor(Vendor vendor, long id);

    List<MultimediaDto> getVendorFiles(long id);

    ResponseEntity<String> approveVendor(long id);

    List<VendorDto> getAllVendors();

    List<VendorDto> getNonApprovedVendors();
}
