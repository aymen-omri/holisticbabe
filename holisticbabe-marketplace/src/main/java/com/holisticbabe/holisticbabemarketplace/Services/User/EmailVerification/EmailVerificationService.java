package com.holisticbabe.holisticbabemarketplace.Services.User.EmailVerification;

import org.springframework.http.ResponseEntity;

public interface EmailVerificationService {
    ResponseEntity<String> verifyEmail(VerifyEmailRequest verifyEmailRequest);

    ResponseEntity<String> resentToken(String email);

    ResponseEntity<Boolean> isEmailVerified(String email);
}
