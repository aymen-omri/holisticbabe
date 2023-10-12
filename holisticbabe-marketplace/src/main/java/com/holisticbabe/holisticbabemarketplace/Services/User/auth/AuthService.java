package com.holisticbabe.holisticbabemarketplace.Services.User.auth;

import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<?> registerUser(RegisterRequest registerRequest);

    ResponseEntity<?> loginUser(LoginRequest LoginRequest);
}
