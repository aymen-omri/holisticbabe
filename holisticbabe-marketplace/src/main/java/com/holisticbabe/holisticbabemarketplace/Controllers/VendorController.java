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
    public ResponseEntity<String> saveVendor(@RequestParam("addressLine1") String addressLine1,
            @RequestParam("addressLine2") String addressLine2,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("id_country") long id_country,
            @RequestParam("companyName") String companyName,
            @RequestParam("identificationNumber") String identificationNumber,
            @RequestParam("vendorDescription") String vendorDescription,
            @RequestParam("shopLink") String shopLink,
            @RequestParam("id_user") long id_user,
            @RequestParam("countries") List<String> countries,
            @RequestParam("files") List<MultipartFile> files) {

        return vendorService.saveVendor(addressLine1, addressLine2, city, postalCode, id_country,
                companyName, identificationNumber, vendorDescription, shopLink, id_user, countries, files);
    }

    @PutMapping("/approve/{id}")
    public ResponseEntity<String> approveVendor(@PathVariable long id) {
        ResponseEntity<String> responseEntity = vendorService.approveVendor(id);
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
