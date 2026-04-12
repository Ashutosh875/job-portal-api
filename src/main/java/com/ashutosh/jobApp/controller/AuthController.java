package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.dto.AuthResponse;
import com.ashutosh.jobApp.dto.LoginRequest;
import com.ashutosh.jobApp.dto.RegisterRequest;
import com.ashutosh.jobApp.entity.User;
import com.ashutosh.jobApp.enums.Role;
import com.ashutosh.jobApp.repository.UserRepository;
import com.ashutosh.jobApp.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register/applicant")
    public ResponseEntity<AuthResponse> registerApplicant(@RequestBody RegisterRequest registerRequest){

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_APPLICANT);

        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse(jwtUtil.generateToken(user));

        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/register/company")
    public ResponseEntity<AuthResponse> registerCompany(@RequestBody RegisterRequest registerRequest){

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_COMPANY);

        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse(jwtUtil.generateToken(user));

        return ResponseEntity.ok().body(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail() , loginRequest.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        AuthResponse authResponse = new AuthResponse(jwtUtil.generateToken(user));

        return ResponseEntity.ok().body(authResponse);

    }
}
