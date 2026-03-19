package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    Page<Company> getAllCompanies(Pageable pageable);

    Company createCompany(Company company);

    void deleteCompany(Long id);

    Company updateCompany(Company company, Long id);

    Company getCompanyById(Long id);
}
