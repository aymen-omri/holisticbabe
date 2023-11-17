package com.holisticbabe.holisticbabemarketplace.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.holisticbabe.holisticbabemarketplace.Dtos.UserDto;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Requests.SuccessMessageRequest;
import com.holisticbabe.holisticbabemarketplace.Requests.UpdatePasswordRequest;
import com.holisticbabe.holisticbabemarketplace.Services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/by-id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto userDto = userService.findUserById(id);
        if (userDto != null) {
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.status(404).body(null);
    }

    @GetMapping("/by-email")
    public ResponseEntity<?> getUserByEmail(@RequestParam("email") String email) {
        try {
            UserDto userDto = userService.getUserByEmail(email);
            if (userDto != null) {
                return ResponseEntity.ok(userDto);
            }
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody _User user, @PathVariable long id) {
        return userService.updateUser(user, id);
    }

    @PutMapping("/disable-enable/{id}")
    public ResponseEntity<String> disableAndEnableUser(@PathVariable long id) {
        try {
            userService.disableAndEnableUser(id);
            return ResponseEntity.ok("User status updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/profile-picture")
    public ResponseEntity<SuccessMessageRequest> uploadProfilePicture(@PathVariable long id,
            @RequestParam("file") MultipartFile file) {
        userService.addProfilePicture(id, file);
        return ResponseEntity.ok(new SuccessMessageRequest(200, "Profile picture uploaded successfully!"));
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable long id,
            @RequestBody UpdatePasswordRequest request) {
        return userService.updateUserPassword(request, id);
    }
}
