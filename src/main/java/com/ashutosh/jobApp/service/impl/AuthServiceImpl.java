package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.dto.AuthResponse;
import com.ashutosh.jobApp.dto.LoginRequest;
import com.ashutosh.jobApp.dto.RegisterRequest;
import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.entity.User;
import com.ashutosh.jobApp.enums.Role;
import com.ashutosh.jobApp.exception.EmailAlreadyExistsException;
import com.ashutosh.jobApp.repository.ApplicantRepository;
import com.ashutosh.jobApp.repository.CompanyRepository;
import com.ashutosh.jobApp.repository.UserRepository;
import com.ashutosh.jobApp.security.util.JwtUtil;
import com.ashutosh.jobApp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager manager;
    private final UserDetailsService userDetailsService;
    private final ApplicantRepository applicantRepository;
    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public AuthResponse registerApplicant(RegisterRequest registerRequest) {

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_APPLICANT);

        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email Already Registered");
        }

        userRepository.save(user);

        Applicant applicant = new Applicant();
        applicant.setUser(user);
        applicantRepository.save(applicant);

        return new AuthResponse(jwtUtil.generateToken(user));
    }

    @Override
    @Transactional
    public AuthResponse registerCompany(RegisterRequest registerRequest) {

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_COMPANY);

        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new EmailAlreadyExistsException("Email Already Registered");
        }

        userRepository.save(user);

        Company company = new Company();
        company.setUser(user);
        companyRepository.save(company);

        return new AuthResponse(jwtUtil.generateToken(user));
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {

        manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail() , loginRequest.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        return new AuthResponse(jwtUtil.generateToken(user));
    }
}
