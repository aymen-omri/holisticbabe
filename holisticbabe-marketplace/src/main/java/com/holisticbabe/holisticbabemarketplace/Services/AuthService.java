package com.holisticbabe.holisticbabemarketplace.Services;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Requests.LoginRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.RegisterRequest;

public interface AuthService {

    ResponseEntity<?> registerUser(RegisterRequest registerRequest);

    ResponseEntity<?> loginUser(LoginRequest LoginRequest);
}
