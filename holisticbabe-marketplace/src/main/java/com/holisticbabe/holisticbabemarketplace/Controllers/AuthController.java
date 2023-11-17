package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Requests.LoginRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.RegisterRequest;
import com.holisticbabe.holisticbabemarketplace.Services.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return authService.loginUser(loginRequest, response);
    }

    @GetMapping("/get-token")
    public ResponseEntity<String> getUserToken(HttpServletRequest request) {
        String token = authService.getTokenFromCookies(request);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(token);
    }

    @GetMapping("/is-logged-in")
    public boolean isLoggedIn(HttpServletRequest request) {
        return authService.isLoggedIn(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        try {
            authService.logout(response);
            return ResponseEntity.status(200).body("Logged out!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestParam("token") String token, HttpServletResponse response) {
        try {
            return authService.googleLogin(token, response);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @GetMapping("/current-user")
    public ResponseEntity<?> currentUser(HttpServletRequest request) {
        Object user = authService.getCurrentUser(request);
        if (user == null) {
            return ResponseEntity.status(401).body("no");
        }
        return ResponseEntity.ok(user);
    }
}
