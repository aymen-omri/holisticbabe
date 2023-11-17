package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Models.EmailVerification;
import com.holisticbabe.holisticbabemarketplace.Repositories.EmailVerificationRepository;
import com.holisticbabe.holisticbabemarketplace.Requests.SuccessMessageRequest;
import com.holisticbabe.holisticbabemarketplace.Services.EmailVerificationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;

    @Override
    @Transactional
    public ResponseEntity<?> verifyEmail(String token) {
        Optional<EmailVerification> emailVerif = emailVerificationRepository.findByVerifToken(token);
        if (emailVerif.isEmpty()) {
            return new ResponseEntity<>("Invalid Token", HttpStatus.BAD_REQUEST);
        }
        if (emailVerif.get().getStatus()) {
            return new ResponseEntity<>(new SuccessMessageRequest(200, "This account is already verified!"),
                    HttpStatus.OK);
        }
        emailVerif.get().setStatus(true);
        return new ResponseEntity<>(new SuccessMessageRequest(200, "Account verified successfully!"), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> isVerified(String email) {
        Optional<EmailVerification> emailVerif = emailVerificationRepository.findByUserEmail(email);
        if (!emailVerif.isPresent() || !emailVerif.get().getStatus()) {
            return new ResponseEntity<>("Not Verified!", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new SuccessMessageRequest(200, "Verified"), HttpStatus.OK);
    }

}
