package com.holisticbabe.holisticbabemarketplace.Services.User.ResetPassword;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifTokenRequest {
    private String email;
    private String tokenText;
}
