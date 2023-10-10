package com.holisticbabe.holisticbabemarketplace.Services.User;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.User.UserDto;
import com.holisticbabe.holisticbabemarketplace.Models.Shared.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Models.User._Role;
import com.holisticbabe.holisticbabemarketplace.Models.User._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.Shared.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.User.RoleRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.User.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final FileUploadService fileUploadService;
    private final MultimediaRepository multimediaRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDto findUserById(long id) {
        _User user = userRepository.findById(id).get();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(user -> modelMapper
                        .map(user, UserDto.class))
                .toList();
    }

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

        userRepository.save(user);

        return ResponseEntity.status(200).body("User registered successfully!");

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

    @Override
    public ResponseEntity<String> updateUser(_User user, long id) {

        Optional<_User> userToUpdate = userRepository.findById(id);

        if (userToUpdate.isPresent()) {

            var user1 = userRepository.findByPhoneNumber(user.getPhoneNumber());
            if (user1.isPresent() && !user1.equals(userToUpdate)) {
                return ResponseEntity.status(409).body("Phone number taken!");
            }

            var user2 = userRepository.findByEmail(user.getEmail());
            if (user2.isPresent() && !user2.equals(userToUpdate)) {
                return ResponseEntity.status(409).body("Email taken!");
            }

            var user3 = userRepository.findByUsername(user.getUsername());
            if (user3.isPresent() && !user3.equals(userToUpdate)) {
                return ResponseEntity.status(409).body("Username taken!");
            }
            userRepository.save(userToUpdate.get());
            return ResponseEntity.ok().body("User updated successfully!");
        }
        return ResponseEntity.status(404).body("User doesn't exist!");
    }

    @Override
    public void disableAndEnableUser(long id) {
        _User user = userRepository.findById(id).get();
        user.setEnabled(!user.isEnabled()); // this will disable the account but not delete it from database!
        userRepository.saveAndFlush(user);
    }

    @Override
    public void addProfilePicture(long id, MultipartFile file) {
        try {
            _User user = userRepository.findById(id).get();
            var imageUrl = fileUploadService.uploadFile(file);
            Multimedia profilePicture = new Multimedia();
            profilePicture.setName(file.getOriginalFilename());
            profilePicture.setType(file.getContentType());
            profilePicture.setUrl(imageUrl);
            Multimedia savedProfilePicture = multimediaRepository.save(profilePicture);
            user.setImage(savedProfilePicture);
            userRepository.save(user);
        } catch (IOException e) {
            e.printStackTrace();
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
