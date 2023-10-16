package com.holisticbabe.holisticbabemarketplace.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.holisticbabe.holisticbabemarketplace.Services.User.EmailVerification.EmailVerificationService;
import com.holisticbabe.holisticbabemarketplace.Services.User.EmailVerification.VerifyEmailRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    @PostMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestBody VerifyEmailRequest request) {
        return emailVerificationService.verifyEmail(request);
    }

    @PostMapping("/resend-token")
    public ResponseEntity<String> resendToken(@RequestParam String email) {
        return emailVerificationService.resentToken(email);
    }

    @GetMapping("/is-verified")
    public ResponseEntity<Boolean> isEmailVerified(@RequestParam String email) {
        return emailVerificationService.isEmailVerified(email);
    }
}
