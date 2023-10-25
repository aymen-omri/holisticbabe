package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Models.Vendor;
import com.holisticbabe.holisticbabemarketplace.Repositories.VendorRepository;
import com.holisticbabe.holisticbabemarketplace.Services.VendorService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;

    @Override
    public Vendor getVendorById(long id) {
        return vendorRepository.findById(id).orElse(null);
    }

    @Override
    public void saveVendor(Vendor vendor) {
        vendor.setApproved(false);
        vendorRepository.save(vendor);
    }

    @Override
    public ResponseEntity<String> updateVendor(Vendor vendor, long id) {
        Optional<Vendor> vendorToUpdate = vendorRepository.findById(id);
        if (vendorToUpdate.isEmpty()) {
            return ResponseEntity.status(404).body("Vendor doesn't exist!");
        }
        vendorToUpdate.get().setCompanyName(vendor.getCompanyName());
        vendorToUpdate.get().setDescription(vendor.getDescription());
        vendorToUpdate.get().setShopLink(vendor.getShopLink());
        return ResponseEntity.ok("Updated successfully!");
    }

    @Override
    public ResponseEntity<String> approveVendor(long id) {
        Optional<Vendor> vendorToApprove = vendorRepository.findById(id);
        if (vendorToApprove.isEmpty()) {
            return ResponseEntity.status(404).body("Vendor doesn't exist!");
        }
        vendorToApprove.get().setApproved(true);
        return ResponseEntity.ok("Approved Successfully");
    }

    @Override
    public List<Vendor> getNonApprovedVendors() {
        return vendorRepository.getNonApprovedVendors();
    }

}
