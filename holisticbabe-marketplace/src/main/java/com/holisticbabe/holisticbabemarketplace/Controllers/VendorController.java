package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.VendorDto;
import com.holisticbabe.holisticbabemarketplace.Services.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vendor")
@RequiredArgsConstructor
public class VendorController {

    private final VendorService vendorService;

    @GetMapping("/{id}")
    public ResponseEntity<VendorDto> getVendorById(@PathVariable long id) {
        VendorDto vendor = vendorService.getVendorById(id);
        if (vendor != null) {
            return ResponseEntity.ok(vendor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<VendorDto> getVendorByUserId(@PathVariable long id) {
        VendorDto vendor = vendorService.getVendorByUserId(id);
        if (vendor != null) {
            return ResponseEntity.ok(vendor);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveVendor(
            @RequestParam(value = "addressLine1", required = false) String addressLine1,
            @RequestParam(value = "addressLine2", required = false) String addressLine2,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "postalCode", required = false) String postalCode,
            @RequestParam(value = "id_country", required = false) String id_country,
            @RequestParam(value = "companyName", required = false) String companyName,
            @RequestParam(value = "identificationNumber", required = false) String identificationNumber,
            @RequestParam(value = "vendorDescription") String vendorDescription,
            @RequestParam(value = "shopLink", required = false) String shopLink,
            @RequestParam(value = "id_user") String id_user,
            @RequestParam(value = "countries") List<String> countries,
            @RequestParam(value = "files") List<MultipartFile> files) {

        return vendorService.saveVendor(addressLine1, addressLine2, city, postalCode, id_country,
                companyName, identificationNumber, vendorDescription, shopLink, id_user, countries, files);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveVendor(@PathVariable long id) {
        ResponseEntity<?> responseEntity = vendorService.approveVendor(id);
        return responseEntity;
    }

    @GetMapping("/non-approved")
    public ResponseEntity<List<VendorDto>> getNonApprovedVendors() {
        List<VendorDto> vendors = vendorService.getNonApprovedVendors();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/all")
    public ResponseEntity<List<VendorDto>> getAllVendor() {
        List<VendorDto> vendors = vendorService.getAllVendors();
        return ResponseEntity.ok(vendors);
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<List<MultimediaDto>> getVendorFiles(@PathVariable long id) {
        List<MultimediaDto> multimedias = vendorService.getVendorFiles(id);
        return ResponseEntity.ok(multimedias);
    }
}
