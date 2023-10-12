package com.holisticbabe.holisticbabemarketplace.Services.User.auth;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Models.User.EmailVerification;
import com.holisticbabe.holisticbabemarketplace.Models.User._Role;
import com.holisticbabe.holisticbabemarketplace.Models.User._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.User.EmailVerificationRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.User.RoleRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.User.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Utlis.MailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final EmailVerificationRepository emailVerificationRepository;

    @Override
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {

        if (userRepository.findByPhoneNumber(registerRequest.getPhoneNumber()).isPresent()) {
            return ResponseEntity.status(409).body("Phone number already exists!");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email already exists!");
        }

        if (userRepository.findByUsername(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists!");
        }

        var roleName = "ROLE_USER";
        _Role role = roleRepository.findByRoleName(roleName);
        EmailVerification emailVerification = new EmailVerification();
        String randomToken = UUID.randomUUID().toString().substring(0, 20);
        emailVerification.setVarif_token(randomToken);
        emailVerification.setExpiryDate(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        emailVerification.setStatus(false);

        _User user = _User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .enabled(true)
                .role(role)
                .build();

        var savedUser = userRepository.save(user);

        emailVerification.setUser(savedUser);
        var emailVerif = emailVerificationRepository.save(emailVerification);

        try {
            mailService.sendEmail(savedUser.getEmail(), "Email Verification",
                    "Go to this link this is your token: " + emailVerif.getVarif_token());
            return ResponseEntity.status(200).body("User registered successfully!");

        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequest LoginRequest) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            LoginRequest.getEmail(), LoginRequest.getPassword()));

            return ResponseEntity.status(200).body(new token(createToken(auth)));

        } catch (DisabledException e) {
            return ResponseEntity.status(403).body("Account disabled contact support for more details!");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(400).body("Wrong email or password please try again!");
        }
    }

    private String createToken(Authentication authentication) {
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 30))
                .subject(authentication.getName())
                .claim("scope", createScope(authentication))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }

    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(" "));
    }

}

record token(String token) {
}
