package com.holisticbabe.holisticbabemarketplace.Services.User.UserManagement;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.User.UserDto;
import com.holisticbabe.holisticbabemarketplace.Models.Shared.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Models.User._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.Shared.MultimediaRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.User.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Utlis.FileUploadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final FileUploadService fileUploadService;
    private final MultimediaRepository multimediaRepository;

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

}
