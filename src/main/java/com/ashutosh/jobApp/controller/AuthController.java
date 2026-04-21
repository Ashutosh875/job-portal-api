package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.dto.request.RegisterApplicantDto;
import com.ashutosh.jobApp.dto.request.RegisterCompanyDto;
import com.ashutosh.jobApp.dto.response.AuthResponseDto;
import com.ashutosh.jobApp.dto.request.LoginRequest;
import com.ashutosh.jobApp.dto.request.RegisterRequest;
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
    public ResponseEntity<AuthResponseDto> registerApplicant(@RequestBody RegisterApplicantDto registerRequest){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.registerApplicant(registerRequest));
    }

    @PostMapping("/register/company")
    public ResponseEntity<AuthResponseDto> registerCompany(@RequestBody RegisterCompanyDto registerRequest){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.registerCompany(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequest loginRequest){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.login(loginRequest));

    }
}
