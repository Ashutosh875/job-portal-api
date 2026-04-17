package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.dto.AuthResponse;
import com.ashutosh.jobApp.dto.LoginRequest;
import com.ashutosh.jobApp.dto.RegisterRequest;
import com.ashutosh.jobApp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/applicant")
    public ResponseEntity<AuthResponse> registerApplicant(@RequestBody RegisterRequest registerRequest){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.registerApplicant(registerRequest));
    }

    @PostMapping("/register/company")
    public ResponseEntity<AuthResponse> registerCompany(@RequestBody RegisterRequest registerRequest){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.registerCompany(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(loginRequest));

    }
}
