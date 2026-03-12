package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.repository.CompanyRepository;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAllWithReviews();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("company with id : " + id + " Not found"));
    }

    @Transactional
    @Override
    public void deleteCompany(Long id) {
        Company company = getCompanyById(id);
        companyRepository.delete(company);
    }

    @Transactional
    @Override
    public Company updateCompany(Company updatedCompany, Long id) {
        Company company = getCompanyById(id);
        company.setDescription(updatedCompany.getDescription());
        company.setName(updatedCompany.getName());
        return companyRepository.save(company);
    }


}
