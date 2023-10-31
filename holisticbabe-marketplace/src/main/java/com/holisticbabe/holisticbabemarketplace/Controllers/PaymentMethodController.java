package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.PaymentMethodDto;
import com.holisticbabe.holisticbabemarketplace.Services.PayementMethodService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-method")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PayementMethodService paymentMethodService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<PaymentMethodDto>> getPaymentMethodsByUserId(@PathVariable long id) {
        List<PaymentMethodDto> paymentMethods = paymentMethodService.findByUserId(id);
        return ResponseEntity.ok(paymentMethods);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodDto> getPaymentMethodById(@PathVariable long id) {
        try {
            PaymentMethodDto paymentMethod = paymentMethodService.findById(id);
            return ResponseEntity.ok(paymentMethod);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPaymentMethod(@RequestBody PaymentMethodDto paymentMethodDto) {
        try {
            paymentMethodService.insertPaymentMethod(paymentMethodDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Payment method added successfully!");
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding payment method", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePaymentMethod(@PathVariable long id) {
        try {
            paymentMethodService.deletePaymentMethod(id);
            return ResponseEntity.ok("Payment method deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting the payment method");
        }
    }

    @PutMapping("/{id_payment_method}/payment-type/{id_payment_type}")
    public ResponseEntity<String> updatePaymentMethod(@PathVariable("id_payment_method") long id_payment_method,
            @PathVariable("id_payment_type") long id_payment_type,
            @RequestBody PaymentMethodDto paymentMethodDto) {
        try {
            paymentMethodService.updatePaymentMethod(id_payment_type, id_payment_method, paymentMethodDto);
            return ResponseEntity.ok("Payment method updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating the payment method");
        }
    }
}
