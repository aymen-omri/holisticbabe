package com.holisticbabe.holisticbabemarketplace.Dtos.User;

import java.time.LocalDate;

public record TokenDto(
        long id_token,
        String token,
        LocalDate expiryDate) {

}
