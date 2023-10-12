package com.holisticbabe.holisticbabemarketplace.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    @GetMapping("/home")
    public String helloWorld() {
        return "Hello world!";
    }
}
