package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.UserDto;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Requests.UpdatePasswordRequest;

public interface UserService {
    UserDto findUserById(long id);

    UserDto getUserByEmail(String email);

    List<UserDto> findAllUsers();

    ResponseEntity<?> updateUser(_User user, long id);

    ResponseEntity<?> updateUserPassword(UpdatePasswordRequest request , long id_user);

    void disableAndEnableUser(long id);

    void addProfilePicture(long id, MultipartFile file);
}
