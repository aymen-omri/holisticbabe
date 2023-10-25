package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Vendor;
import com.holisticbabe.holisticbabemarketplace.Services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable long id) {
        Vendor vendor = vendorService.getVendorById(id);
        if (vendor != null) {
            return ResponseEntity.ok(vendor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveVendor(@RequestBody Vendor vendor) {
        try {
            vendorService.saveVendor(vendor);
            return ResponseEntity.status(HttpStatus.CREATED).body("Vendor saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving vendor.");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateVendor(@RequestBody Vendor vendor, @PathVariable long id) {
        ResponseEntity<String> responseEntity = vendorService.updateVendor(vendor, id);
        return responseEntity;
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveVendor(@PathVariable long id) {
        ResponseEntity<String> responseEntity = vendorService.approveVendor(id);
        return responseEntity;
    }

    @GetMapping("/non-approved")
    public ResponseEntity<List<Vendor>> getNonApprovedVendors() {
        List<Vendor> vendors = vendorService.getNonApprovedVendors();
        return ResponseEntity.ok(vendors);
    }
}
