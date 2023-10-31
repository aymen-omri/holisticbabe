package com.holisticbabe.holisticbabemarketplace.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private String email;
    private String tokenText;
    private String newPassword;
    private String confirmPassword;
}
