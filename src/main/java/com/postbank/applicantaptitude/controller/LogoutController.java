package com.postbank.applicantaptitude.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LogoutController {

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();  // Clear authentication
        request.getSession().invalidate(); // Invalidate session

        Map<String, String> res = new HashMap<>();
        res.put("message", "Logged out successfully");

        return ResponseEntity.ok(res);
    }
}