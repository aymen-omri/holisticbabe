package com.holisticbabe.holisticbabemarketplace.Impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Models.Token;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.TokenRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Requests.ResetPasswordRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.VerifTokenRequest;
import com.holisticbabe.holisticbabemarketplace.Services.ResetPasswordService;
import com.holisticbabe.holisticbabemarketplace.Utlis.MailService;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResetPasswordImpl implements ResetPasswordService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final PasswordEncoder passwordEncoder;

    private String generateToken() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + 1000 * 60 * 10);
    }

    @Transactional
    @Override
    public ResponseEntity<?> sendToken(String email) {
        Optional<_User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            String tokenText = this.generateToken();
            Token tokenToSave = new Token();
            tokenToSave.setToken(tokenText);
            tokenToSave.setExpiryDate(this.getExpirationDate());
            tokenToSave.setUser(user.get());
            Token token = tokenRepository.save(tokenToSave);
            try {
                mailService.sendEmail(user.get().getEmail(), "Reset_Password",
                        "Here is your reset token: " + token.getToken());
            } catch (MessagingException e) {
                return ResponseEntity.status(500).body(e.getMessage());
            }
            var response = new HashMap<String, String>();
            response.put("message", "Token received successfully!");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(404).body("Email not found!");
    }

    @Override
    public ResponseEntity<?> verifToken(VerifTokenRequest request) {
        _User user = userRepository.findByEmail(request.getEmail()).get();
        Date dateNow = new Date(System.currentTimeMillis());
        Date expDateCurrentToken = user.getToken().getExpiryDate();
        int result = dateNow.compareTo(expDateCurrentToken);
        if (result > 0) {
            return ResponseEntity.status(401).body("Token expired!");
        }
        if (!request.getTokenText().equals(user.getToken().getToken())) {
            return ResponseEntity.status(401).body("Invalid token!");
        }
        var response = new HashMap<String, String>();
        response.put("message", "Token is valid");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordRequest request) {
        _User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("No such user exists!");
        }
        if (request.getNewPassword().equals(request.getConfirmPassword()) &&
                request.getTokenText().equals(user.getToken().getToken())) {

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            tokenRepository.deleteById(user.getToken().getId_token());
            var response = new HashMap<String, String>();
            response.put("message", "Password changed successfully!");
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Something went wrong!");
    }
}
