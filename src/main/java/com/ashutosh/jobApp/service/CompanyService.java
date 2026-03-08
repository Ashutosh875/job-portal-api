package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.entity.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    Company createCompany(Company company);

    void deleteCompany(Long id);

    Company updateCompany(Company company, Long id);

    Company getCompanyById(Long id);
}
