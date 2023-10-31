package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.PaymentTypeDto;
import com.holisticbabe.holisticbabemarketplace.Services.PaymenTypeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-type")
@RequiredArgsConstructor
public class PaymentTypeController {

    private final PaymenTypeService paymentTypeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPaymentTypeById(@PathVariable long id) {
        try {
            PaymentTypeDto paymentType = paymentTypeService.findById(id);
            return ResponseEntity.ok(paymentType);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentTypeDto>> getAllPaymentTypes() {
        List<PaymentTypeDto> paymentTypes = paymentTypeService.findAll();
        return ResponseEntity.ok(paymentTypes);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPaymentType(@RequestBody PaymentTypeDto paymentTypeDto) {
        try {
            paymentTypeService.insertPaymentType(paymentTypeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Payment type added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePaymentType(@PathVariable long id) {
        try {
            paymentTypeService.deletePaymentType(id);
            return ResponseEntity.ok("Payment type deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatePaymentType(@PathVariable long id, @RequestBody PaymentTypeDto paymentTypeDto) {
        try {
            // Validation logic here if needed
            paymentTypeService.updatePaymentType(id, paymentTypeDto);
            return ResponseEntity.ok("Payment type updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
