package com.holisticbabe.holisticbabemarketplace.Security;

import org.springframework.stereotype.Service;

import com.nimbusds.jwt.SignedJWT;

@Service
public class ValidateJwtGoogle {

    public static Object validateGoogleJwt(String jwt) throws Exception {

        try {
            var signedJWT = SignedJWT.parse(jwt);
            return signedJWT;
        } catch (java.text.ParseException e) {
            return e.getMessage();
        }
    }

}
