package com.holisticbabe.holisticbabemarketplace.Services.User.EmailVerification;

import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Models.User.EmailVerification;
import com.holisticbabe.holisticbabemarketplace.Repositories.User.EmailVerificationRepository;
import com.holisticbabe.holisticbabemarketplace.Utlis.MailService;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService {

    private final EmailVerificationRepository emailVerificationRepository;
    private final MailService mailService;

    @Override
    @Transactional
    public ResponseEntity<String> verifyEmail(VerifyEmailRequest request) {
        // get the email verification object
        EmailVerification emailVerification = emailVerificationRepository.findByUserEmail(request.getEmail())
                .orElse(null);
        // verify if it is not null
        if (emailVerification == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Email Verification object!");
        }
        // check if token is valid
        if (!emailVerification.getVerif_token().equals(request.getToken())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }
        // check if token is not expired
        Date currentDate = new Date(System.currentTimeMillis());
        Date expiryDate = emailVerification.getExpiryDate();
        int result = currentDate.compareTo(expiryDate);
        if (result > 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Link has already been used or is expired!");
        }
        // Update the user's verified status to true and save it in DB
        emailVerification.setStatus(true);
        return ResponseEntity.status(HttpStatus.OK).body("Email verified successfully!");

    }

    @Override
    @Transactional
    public ResponseEntity<String> resentToken(String email) {
        // get the email verification object
        EmailVerification emailVerification = emailVerificationRepository.findByUserEmail(email)
                .orElse(null);
        // verify if it is not null
        if (emailVerification == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Email Verification Object!");
        }
        // Generate a new token and send it to the user
        String tokenText = UUID.randomUUID().toString().substring(0, 30);
        emailVerification.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        emailVerification.setVerif_token(tokenText);
        try {
            mailService.sendEmail(email, "Verify your email address",
                    "Here is your reset token: " + emailVerification.getVerif_token());
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
        return ResponseEntity.ok("A new token has been sent to you.");
    }

    @Override
    public ResponseEntity<Boolean> isEmailVerified(String email) {
        EmailVerification mailVerification = emailVerificationRepository.findByUserEmail(email).orElse(null);
        if (mailVerification == null) {
            return ResponseEntity.status(404).body(false);
        }
        return ResponseEntity.ok(mailVerification.getStatus());
    }

}
