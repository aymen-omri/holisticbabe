package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.UserDto;
import com.holisticbabe.holisticbabemarketplace.Models._User;

public interface UserService {
    UserDto findUserById(long id);

    List<UserDto> findAllUsers();

    ResponseEntity<String> updateUser(_User user, long id);

    void disableAndEnableUser(long id);

    void addProfilePicture(long id , MultipartFile file);
}
