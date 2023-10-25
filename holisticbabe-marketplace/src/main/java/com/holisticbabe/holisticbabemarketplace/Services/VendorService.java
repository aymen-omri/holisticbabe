package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Models.Vendor;

public interface VendorService {
    Vendor getVendorById(long id);

    void saveVendor(Vendor vendor);

    ResponseEntity<String> updateVendor(Vendor vendor, long id);

    ResponseEntity<String> approveVendor(long id);

    List<Vendor> getNonApprovedVendors();
}
