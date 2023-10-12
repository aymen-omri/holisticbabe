package com.holisticbabe.holisticbabemarketplace.Dtos.User;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
        private long idToken;
        private String token;
        private Date expiryDate;
}
