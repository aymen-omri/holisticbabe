package com.holisticbabe.holisticbabemarketplace.Services.User;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.User.UserDto;
import com.holisticbabe.holisticbabemarketplace.Models.User._User;

public interface UserService {
    UserDto findUserById(long id);

    List<UserDto> findAllUsers();

    ResponseEntity<?> registerUser(RegisterRequest registerRequest);

    ResponseEntity<?> loginUser(LoginRequest LoginRequest);

    ResponseEntity<String> updateUser(_User user, long id);

    void disableAndEnableUser(long id);

    void addProfilePicture(long id , MultipartFile file);
}
