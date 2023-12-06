package com.holisticbabe.holisticbabemarketplace.Services;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Requests.ResetPasswordRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.VerifTokenRequest;

public interface ResetPasswordService {

    ResponseEntity<String> sendToken(String email);

    ResponseEntity<String> verifToken(VerifTokenRequest request);

    ResponseEntity<String> resetPassword(ResetPasswordRequest request);

}
