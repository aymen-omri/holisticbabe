package com.holisticbabe.holisticbabemarketplace.Services.User.ResetPassword;

import org.springframework.http.ResponseEntity;

public interface ResetPasswordService {

    ResponseEntity<String> sendToken(String email);

    ResponseEntity<String> verifToken(VerifTokenRequest request);

    ResponseEntity<String> resetPassword(ResetPasswordRequest request);

}
