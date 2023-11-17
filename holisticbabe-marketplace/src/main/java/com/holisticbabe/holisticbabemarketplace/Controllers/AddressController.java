package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.AddressDto;
import com.holisticbabe.holisticbabemarketplace.Models.Address;
import com.holisticbabe.holisticbabemarketplace.Requests.SuccessMessageRequest;
import com.holisticbabe.holisticbabemarketplace.Services.AddressService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable long id) {
        // NoSuchElementException
        AddressDto addressDto = addressService.findAddressById(id);
        if (addressDto != null) {
            return ResponseEntity.ok(addressDto);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<AddressDto>> getAddressesByUserId(@PathVariable long id) {
        List<AddressDto> addresses = addressService.getAllByUserId(id);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/user/{id}")
    public ResponseEntity<?> addAddress(@RequestBody Address address, @PathVariable long id) {
        try {
            addressService.insertAddress(address, id);
            return ResponseEntity.ok(new SuccessMessageRequest(200, "Address added successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PutMapping("/{address_id}/country/{country_id}/user/{user_id}")
    public ResponseEntity<?> updateAddress(@PathVariable("address_id") long address_id,
            @PathVariable("country_id") long country_id, @PathVariable("user_id") long user_id,
            @RequestBody AddressDto addressDto) {
        try {
            addressService.updateAddress(country_id, address_id, user_id, addressDto);
            return ResponseEntity.ok(new SuccessMessageRequest(200, "Address updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update address: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable long id) {
        try {
            addressService.deleteAddress(id);
            return ResponseEntity.ok(new SuccessMessageRequest(200, "Address deleted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete address: " + e.getMessage());
        }
    }
}
