package com.holisticbabe.holisticbabemarketplace.Services;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Requests.ResetPasswordRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.VerifTokenRequest;

public interface ResetPasswordService {

    ResponseEntity<?> sendToken(String email);

    ResponseEntity<?> verifToken(VerifTokenRequest request);

    ResponseEntity<?> resetPassword(ResetPasswordRequest request);

}
