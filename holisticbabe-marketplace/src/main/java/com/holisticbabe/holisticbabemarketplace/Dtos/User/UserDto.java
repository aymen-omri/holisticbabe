package com.holisticbabe.holisticbabemarketplace.Dtos.User;

import java.math.BigInteger;


public record UserDto(
        long id_user,
        String firstName,
        String lastName,
        String username,
        String email,
        BigInteger phoneNumber,
        MultimediaDto image,
        RoleDto role
        ) {
}
