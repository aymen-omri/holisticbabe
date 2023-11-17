package com.holisticbabe.holisticbabemarketplace.Impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.UserDto;
import com.holisticbabe.holisticbabemarketplace.Models.EmailVerification;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.EmailVerificationRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Requests.SuccessMessageRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.UpdatePasswordRequest;
import com.holisticbabe.holisticbabemarketplace.Services.UserService;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;
import com.holisticbabe.holisticbabemarketplace.Utlis.MailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FileUploadService fileUploadService;
    private final MultimediaRepository multimediaRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

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
    public ResponseEntity<?> updateUser(_User user, long id) {

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
            userToUpdate.get().setFirstName(user.getFirstName());
            userToUpdate.get().setLastName(user.getLastName());
            userToUpdate.get().setUsername(user.getUsername());
            userToUpdate.get().setBirthDate(user.getBirthDate());
            userToUpdate.get().setDescription(user.getDescription());
            userToUpdate.get().setProfession(user.getProfession());

            if (!user.getEmail().equals(userToUpdate.get().getEmail())) {
                EmailVerification emailVerification = emailVerificationRepository
                        .findByUserEmail(userToUpdate.get().getEmail()).get();
                String randomToken = UUID.randomUUID().toString().substring(0, 20);
                emailVerification.setVerifToken(randomToken);
                emailVerification.setStatus(false);
                EmailVerification savedEmailVerificationObject = emailVerificationRepository.save(emailVerification);

                try {
                    mailService.sendEmail(user.getEmail(), "Email Verification",
                            "Go to this link this is your token: " + savedEmailVerificationObject.getVerifToken());

                } catch (MessagingException e) {
                    return ResponseEntity.status(500).body(e.getMessage());
                }
            }
            userToUpdate.get().setEmail(user.getEmail());
            userRepository.save(userToUpdate.get());

            return ResponseEntity.ok(new SuccessMessageRequest(200, "User updated successfully!"));
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
            var imageUrl = fileUploadService.uploadFile(file, "profile-pictures-holisticbabe");
            if (user.getImage() != null) {
                user.getImage().setName(file.getOriginalFilename());
                user.getImage().setType(file.getContentType());
                user.getImage().setUrl(imageUrl);
                multimediaRepository.save(user.getImage());
            } else {
                Multimedia profilePicture = new Multimedia();
                profilePicture.setName(file.getOriginalFilename());
                profilePicture.setType(file.getContentType());
                profilePicture.setUrl(imageUrl);
                Multimedia savedProfilePicture = multimediaRepository.save(profilePicture);
                user.setImage(savedProfilePicture);
                userRepository.save(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public UserDto getUserByEmail(String email) {
        Optional<_User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public ResponseEntity<?> updateUserPassword(UpdatePasswordRequest request, long id_user) {
        Optional<_User> user = userRepository.findById(id_user);
        if (!user.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.get().getEmail(), request.getOldPassword()));
            if (!request.getNewPassword().equals(request.getConfirmPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Passwords do not match!");
            }
            String newPassword = passwordEncoder.encode(request.getNewPassword());
            user.get().setPassword(newPassword);
            userRepository.save(user.get());
            return ResponseEntity.ok(new SuccessMessageRequest(200, "Password updated successfully!"));
        } catch (DisabledException e) {
            return ResponseEntity.status(403).body("Account disabled contact support for more details!");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(400).body("Wrong password please try again!");
        }

    }
}
