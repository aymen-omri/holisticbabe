package com.holisticbabe.holisticbabemarketplace.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class HBUtils {

    private  HBUtils(){

    }
    public  static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}", httpStatus);

    }
}
