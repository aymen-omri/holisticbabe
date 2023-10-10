package com.holisticbabe.holisticbabemarketplace.Services.User;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private long id_user;
    private String firstName;
    private String lastName;
    private String username;
    private BigInteger phoneNumber;
    private String email;
    private String password;
}
