package com.ashutosh.jobApp.company.impl;

import com.ashutosh.jobApp.company.Company;
import com.ashutosh.jobApp.company.CompanyRepository;
import com.ashutosh.jobApp.company.CompanyService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("company with id : " + id + "Not found"));
        companyRepository.delete(company);
    }

    @Override
    public Company updateCompany(Company updatedCompany, Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("company with id : " + id + "Not found"));
        company.setDescription(updatedCompany.getDescription());
        company.setName(updatedCompany.getName());
        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("company with id : " + id + "Not found"));
    }

}
