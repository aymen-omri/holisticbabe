package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.CountryDto;
import com.holisticbabe.holisticbabemarketplace.Services.CountryService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable long id) {
        CountryDto country = countryService.findById(id);
        if (country != null) {
            return ResponseEntity.ok(country);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllCountries() {
        return ResponseEntity.ok(countryService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCountry(@RequestBody CountryDto countryDto) {
        try {
            countryService.insertCountry(countryDto);
            return ResponseEntity.ok("Country added successfully!");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error occurred: " + ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable long id) {
        try {
            countryService.deleteCountry(id);
            return ResponseEntity.ok("Country deleted successfully!");
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error occurred: " + ex.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCountry(@PathVariable long id, @RequestBody CountryDto countryDto) {
        try {
            countryService.updateCountry(id, countryDto);
            return ResponseEntity.ok("Country updated successfully!");
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.status(404).body("Country not found with ID: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(500).body("Error occurred: " + ex.getMessage());
        }
    }
}
