package com.holisticbabe.holisticbabemarketplace.Security;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CookieService {

    public void createCookie(String jwt, HttpServletResponse response) {
        Cookie cookie = new Cookie("AuthToken", jwt);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600); // Cookie expiration time in seconds (1 hour)
        cookie.setPath("/api/v1/auth"); // Cookie is accessible from all paths in the application
        cookie.setDomain("localhost");
        response.addCookie(cookie);
    }

    public void clearCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("AuthToken", "");
        cookie.setPath("/api/v1/auth");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setDomain("localhost");
        response.addCookie(cookie);
    }
}
