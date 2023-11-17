package com.holisticbabe.holisticbabemarketplace.Services;

import org.springframework.http.ResponseEntity;

public interface EmailVerificationService {
    ResponseEntity<?> verifyEmail(String token);

    ResponseEntity<?> isVerified(String email);
}
