package com.holisticbabe.holisticbabemarketplace.Dtos.User;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
        private long id_user;
        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private BigInteger phoneNumber;
        private MultimediaDto image;
        private RoleDto role;
}
