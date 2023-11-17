package com.holisticbabe.holisticbabemarketplace.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.holisticbabe.holisticbabemarketplace.Services.EmailVerificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/verify")
    public ResponseEntity<?> verifyEmail(@RequestParam("email") String token) {
        return emailVerificationService.verifyEmail(token);
    }

    @GetMapping("/is-verified/{email}")
    public ResponseEntity<?> isVerified(@PathVariable String email) {
        return emailVerificationService.isVerified(email);
    }
}
