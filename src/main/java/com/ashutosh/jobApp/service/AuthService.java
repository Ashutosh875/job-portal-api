package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.dto.request.RegisterApplicantDto;
import com.ashutosh.jobApp.dto.request.RegisterCompanyDto;
import com.ashutosh.jobApp.dto.response.AuthResponseDto;
import com.ashutosh.jobApp.dto.request.LoginRequest;

public interface AuthService {

    AuthResponseDto registerApplicant(RegisterApplicantDto registerRequest);

    AuthResponseDto registerCompany(RegisterCompanyDto registerRequest);

    AuthResponseDto login(LoginRequest loginRequest);
}
