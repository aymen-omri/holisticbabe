package com.holisticbabe.holisticbabemarketplace.Impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.MultimediaDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.VendorDto;
import com.holisticbabe.holisticbabemarketplace.Models.Address;
import com.holisticbabe.holisticbabemarketplace.Models.AddressType;
import com.holisticbabe.holisticbabemarketplace.Models.Country;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Models.Vendor;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.AddressRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.CountryRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.VendorRepository;
import com.holisticbabe.holisticbabemarketplace.Services.VendorService;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final UserRepository userRepository;
    private final CountryRepository countryRepository;
    private final AddressRepository addressRepository;
    private final MultimediaRepository multimediaRepository;
    private final FileUploadService fileUploadService;
    private final ModelMapper modelMapper;

    @Override
    public VendorDto getVendorById(long id) {
        Optional<Vendor> vendor = vendorRepository.findById(id);
        if (vendor.isEmpty()) {
            return null;
        }
        return modelMapper.map(vendor.get(), VendorDto.class);
    }

    @Override
    public ResponseEntity<String> saveVendor(String addressLine1, String addressLine2, String city, String postalCode,
            long id_country, String companyName, String identificationNumber, String vendorDescription, String shopLink,
            long id_user, List<String> countries, List<MultipartFile> files) {
        _User user = userRepository.findById(id_user).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        Address companyAddress = new Address();
        companyAddress.setAddressLine1(addressLine1);
        companyAddress.setAddressLine2(addressLine2);
        companyAddress.setAddressType(AddressType.SocietyAddress);
        companyAddress.setCity(city);
        companyAddress.setPostalCode(postalCode);
        companyAddress.setCountry(countryRepository.findById(id_country).get());
        Address savedCompanyAddress = addressRepository.save(companyAddress);

        Vendor vendor = new Vendor();
        vendor.setApproved(false);
        vendor.setCompanyAddress(savedCompanyAddress);
        vendor.setSocietyIdentificationNumber(identificationNumber);
        vendor.setDescription(vendorDescription);
        vendor.setShopLink(shopLink);
        vendor.setCompanyName(companyName);
        vendor.setDate(LocalDate.now());
        vendor.setUser(user);
        // Vendor savedVendor = vendorRepository.save(vendor);

        // List<Country> countriesToSave = new ArrayList<>();
        for (String country : countries) {
            Country countryToUse = countryRepository.findByCountryName(country).get();
            vendor.getCountries().add(countryToUse);
        }
        Vendor savedVendor = vendorRepository.save(vendor);

        for (MultipartFile file : files) {
            try {
                String link = fileUploadService.uploadFile(file, "vendors-files");
                Multimedia fileToSave = new Multimedia();
                fileToSave.setUrl(link);
                fileToSave.setName(file.getOriginalFilename());
                fileToSave.setType(file.getContentType());
                fileToSave.setVendor(savedVendor);
                multimediaRepository.save(fileToSave);
            } catch (IOException e) {
                return ResponseEntity.status(500).body("Error while uploading files");
            }
        }
        return ResponseEntity.ok("Saved successfully!");
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
    public List<VendorDto> getNonApprovedVendors() {
        return vendorRepository.getNonApprovedVendors().stream()
                .map(file -> modelMapper.map(file, VendorDto.class)).toList();
    }

    @Override
    public List<MultimediaDto> getVendorFiles(long id) {
        return multimediaRepository.getFilesByVendorId(id).stream()
                .map(file -> modelMapper.map(file, MultimediaDto.class)).toList();
    }

    @Override
    public List<VendorDto> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(vendor -> modelMapper.map(vendor, VendorDto.class)).toList();
    }

    @Override
    public VendorDto getVendorByUserId(long id) {
        Optional<Vendor> vendor = vendorRepository.getByUserId(id);
        if (vendor.isPresent()) {
            return modelMapper.map(vendor.get(), VendorDto.class);
        }
        return null;
    }

}
