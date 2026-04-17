package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.dto.CompanyProfileRequest;
import com.ashutosh.jobApp.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {

    Page<Company> getAllCompanies(Pageable pageable);

    void deleteCompany();

    Company getCompanyById(Long id);

    Company getAuthenticatedCompany();

    Company updateCompanyProfile(CompanyProfileRequest request);
}
