package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.dto.AuthResponse;
import com.ashutosh.jobApp.dto.LoginRequest;
import com.ashutosh.jobApp.dto.RegisterRequest;

public interface AuthService {

    AuthResponse registerApplicant(RegisterRequest registerRequest);

    AuthResponse registerCompany(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);
}
