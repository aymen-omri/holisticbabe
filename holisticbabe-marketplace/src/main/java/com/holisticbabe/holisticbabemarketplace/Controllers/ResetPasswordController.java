package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Services.User.ResetPassword.ResetPasswordRequest;
import com.holisticbabe.holisticbabemarketplace.Services.User.ResetPassword.ResetPasswordService;
import com.holisticbabe.holisticbabemarketplace.Services.User.ResetPassword.VerifTokenRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reset-password")
@RequiredArgsConstructor
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    @PostMapping("/send-token")
    public ResponseEntity<String> sendResetToken(@RequestParam("email") String email) {
        return resetPasswordService.sendToken(email);
    }

    @PostMapping("/verify-token")
    public ResponseEntity<String> verifyToken(@RequestBody VerifTokenRequest request) {
        return resetPasswordService.verifToken(request);
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        return resetPasswordService.resetPassword(request);
    }
}
