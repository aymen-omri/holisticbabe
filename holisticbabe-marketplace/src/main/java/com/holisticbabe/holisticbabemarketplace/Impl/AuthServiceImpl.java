package com.holisticbabe.holisticbabemarketplace.Impl;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.UserDto;
import com.holisticbabe.holisticbabemarketplace.Models.EmailVerification;
import com.holisticbabe.holisticbabemarketplace.Models._Role;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.EmailVerificationRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.RoleRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Requests.LoginRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.RegisterRequest;
import com.holisticbabe.holisticbabemarketplace.Security.CookieService;
import com.holisticbabe.holisticbabemarketplace.Services.AuthService;
import com.holisticbabe.holisticbabemarketplace.Utlis.MailService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final RoleRepository roleRepository;
    private final MailService mailService;
    private final EmailVerificationRepository emailVerificationRepository;
    private final CookieService cookieService;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> registerUser(RegisterRequest registerRequest) {

        if (userRepository.findByPhoneNumber(registerRequest.getPhoneNumber()).isPresent()) {
            return ResponseEntity.status(409).body("Phone number already exists!");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.status(409).body("Email already exists!");
        }

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(409).body("Username already exists!");
        }

        var roleName = "ROLE_USER";
        _Role role = roleRepository.findByRoleName(roleName);
        EmailVerification emailVerification = new EmailVerification();
        String randomToken = UUID.randomUUID().toString().substring(0, 20);
        emailVerification.setVerifToken(randomToken);
        emailVerification.setStatus(false);

        _User user = _User.builder()
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .memberSince(LocalDate.now())
                .enabled(true)
                .role(role)
                .build();

        var savedUser = userRepository.save(user);

        emailVerification.setUser(savedUser);
        var emailVerif = emailVerificationRepository.save(emailVerification);

        try {
            String verificationLink = "http://localhost:4200/auth/verify/" + emailVerif.getVerifToken();
            String emailContent = "Click the following link to verify your account: "
                    + verificationLink;

            mailService.sendEmail(savedUser.getEmail(), "Email Verification",
                    emailContent);
            var response = new HashMap<String, String>();
            response.put("message", "Successfully registered! Please verify your email");
            return ResponseEntity.status(200).body(response);

        } catch (MessagingException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> loginUser(LoginRequest LoginRequest, HttpServletResponse response) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            LoginRequest.getEmail(), LoginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(auth);
            String JwtToken = createToken(auth, LoginRequest.getEmail());
            cookieService.createCookie(JwtToken, response);

            return ResponseEntity.status(200).body(new token(JwtToken));

        } catch (DisabledException e) {
            return ResponseEntity.status(403).body("Account disabled contact support for more details!");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(400).body("Wrong email or password please try again!");
        }
    }

    private String createToken(Authentication authentication, String email) {
        var claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(60 * 300))
                .subject(email)
                .claim("scope", createScope(authentication))
                .build();

        JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters.from(claims);
        return jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
    }

    private String createScope(Authentication authentication) {
        return authentication.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(" "));
    }

    @Override
    public String getTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String jwtToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("AuthToken")) {
                    jwtToken = cookie.getValue();
                    break;
                }
            }
        }

        if (jwtToken == null) {
            return null;
        }
        return jwtToken;
    }

    @Override
    public boolean isLoggedIn(HttpServletRequest request) {
        String token = getTokenFromCookies(request);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(auth.toString());
        return (auth == null) || (token == null) ? false : true;
    }

    @Override
    public void logout(HttpServletResponse response) {
        cookieService.clearCookie(response);
        SecurityContextHolder.getContext().setAuthentication(null);
        // System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
    }

    @Override
    public ResponseEntity<?> googleLogin(String token, HttpServletResponse response) {
        try {
            var signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            Map<String, Object> claims = claimsSet.getClaims();
            String email = (String) claims.get("email");
            Boolean isVerified = (Boolean) claims.get("email_verified");
            String givenName = (String) claims.get("given_name");
            String familyName = (String) claims.get("family_name");
            System.out.println(email + isVerified + givenName + familyName);
            Optional<_User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        user.get().getEmail(),
                        null,
                        AuthorityUtils.createAuthorityList("ROLE_USER"));
                SecurityContextHolder.getContext().setAuthentication(auth);
                String jwtToken = createToken(auth, user.get().getEmail());
                cookieService.createCookie(jwtToken, response);
                return new ResponseEntity<>(new token(jwtToken), HttpStatus.OK);
            } else {
                var roleName = "ROLE_USER";
                _Role role = roleRepository.findByRoleName(roleName);
                EmailVerification emailVerification = new EmailVerification();
                emailVerification.setStatus(isVerified);
                _User newUser = _User.builder()
                        .firstName(givenName)
                        .lastName(familyName)
                        .username(email)
                        .role(role)
                        .email(email)
                        .memberSince(LocalDate.now())
                        .enabled(true)
                        .build();
                _User savedUser = userRepository.save(newUser);
                emailVerification.setUser(savedUser);
                emailVerificationRepository.save(emailVerification);
                Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), null);
                SecurityContextHolder.getContext().setAuthentication(auth);
                String jwtToken = createToken(auth, savedUser.getEmail());
                cookieService.createCookie(jwtToken, response);
                return new ResponseEntity<>(new token(jwtToken),
                        HttpStatus.CREATED);
            }

        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }

    }

    @Override
    public Object getCurrentUser(HttpServletRequest request) {
        String token = getTokenFromCookies(request);
        String username = jwtDecoder.decode(token).getSubject();
        Optional<_User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }
}

record token(String token) {
}
