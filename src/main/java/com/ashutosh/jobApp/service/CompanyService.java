package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.dto.request.CompanyRequestDto;
import com.ashutosh.jobApp.dto.response.CompanyResponseDto;
import com.ashutosh.jobApp.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    Page<CompanyResponseDto> getAllCompanies(Pageable pageable);

    void deleteCompany();

    CompanyResponseDto getCompanyById(Long id);

    Company getAuthenticatedCompany();

    CompanyResponseDto updateCompanyProfile(CompanyRequestDto request);
}
