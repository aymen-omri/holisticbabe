package com.holisticbabe.holisticbabemarketplace.Services;

import org.springframework.http.ResponseEntity;

import com.holisticbabe.holisticbabemarketplace.Requests.LoginRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.RegisterRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    ResponseEntity<?> registerUser(RegisterRequest registerRequest);

    ResponseEntity<?> loginUser(LoginRequest LoginRequest, HttpServletResponse response);

    String getTokenFromCookies(HttpServletRequest request);

    boolean isLoggedIn(HttpServletRequest request);

    void logout(HttpServletResponse response);

    ResponseEntity<?> googleLogin(String token, HttpServletResponse response);

    Object getCurrentUser(HttpServletRequest request);
}
