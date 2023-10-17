package com.holisticbabe.holisticbabemarketplace.Requests;

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
