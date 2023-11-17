package com.holisticbabe.holisticbabemarketplace.Dtos;

import java.math.BigInteger;
import java.time.LocalDate;

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
        private String description;
        private LocalDate birthDate;
        private String profession;
        private LocalDate memberSince;
        private MultimediaDto image;
        private RoleDto role;
}
