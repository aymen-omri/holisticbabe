package com.holisticbabe.holisticbabemarketplace.Services;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Requests.VerifyEmailRequest;

public interface EmailVerificationService {
    ResponseEntity<String> verifyEmail(VerifyEmailRequest verifyEmailRequest);

    ResponseEntity<String> resentToken(String email);

    ResponseEntity<Boolean> isEmailVerified(String email);
}
