package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.dto.request.RegisterApplicantDto;
import com.ashutosh.jobApp.dto.request.RegisterCompanyDto;
import com.ashutosh.jobApp.dto.response.AuthResponseDto;
import com.ashutosh.jobApp.dto.request.LoginRequest;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
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
    public AuthResponseDto registerApplicant(RegisterApplicantDto registerRequest) {

        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            log.warn("already registered applicant with email {} tried to register again" , registerRequest.getEmail());
            throw new EmailAlreadyExistsException("Email Already Registered");
        }

        log.info("Applicant with email: {} trying to register" , registerRequest.getEmail());

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_APPLICANT);

        userRepository.save(user);

        Applicant applicant = new Applicant();
        applicant.setName(registerRequest.getName());
        applicant.setExperience(registerRequest.getExperience());
        applicant.setJobTitle(registerRequest.getJobTitle());
        applicant.setUser(user);
        applicantRepository.save(applicant);

        log.info("Applicant with email : {} registered successfully" , registerRequest.getEmail());

        return new AuthResponseDto(jwtUtil.generateToken(user));
    }

    @Override
    @Transactional
    public AuthResponseDto registerCompany(RegisterCompanyDto registerRequest) {

        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            log.warn("already registered company with email {} tried to register again" , registerRequest.getEmail());
            throw new EmailAlreadyExistsException("Email Already Registered");
        }

        log.info("Company with email: {} trying to register" , registerRequest.getEmail());

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encoder.encode(registerRequest.getPassword()));
        user.setRole(Role.ROLE_COMPANY);

        userRepository.save(user);

        Company company = new Company();
        company.setName(registerRequest.getName());
        company.setDescription(registerRequest.getDescription());
        company.setUser(user);
        companyRepository.save(company);

        log.info("Company with email : {} registered successfully" , registerRequest.getEmail());

        return new AuthResponseDto(jwtUtil.generateToken(user));
    }

    @Override
    @Transactional
    public AuthResponseDto login(LoginRequest loginRequest) {

        manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail() , loginRequest.getPassword())
        );

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        log.info("User with email : {} logged in successfully" ,loginRequest.getEmail() );

        return new AuthResponseDto(jwtUtil.generateToken(user));
    }
}
