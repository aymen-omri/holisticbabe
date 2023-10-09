package com.holisticbabe.holisticbabemarketplace.Dtos.User;

import java.math.BigInteger;


public record UserDto(
        long id_user,
        String firstName,
        String lastName,
        String username,
        String email,
        BigInteger phoneNumber,
        TokenDto token,
        MultimediaDto image,
        RoleDto role
        ) {
}
