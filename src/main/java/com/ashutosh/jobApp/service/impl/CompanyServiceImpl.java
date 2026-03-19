package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.repository.CompanyRepository;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Company> getAllCompanies(Pageable pageable) {
        return companyRepository.findAllWithReviews(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("company with id : " + id + " Not found"));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteCompany(Long id) {
        Company company = getCompanyById(id);
        company.setActive(false);
        companyRepository.save(company);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Company updateCompany(Company updatedCompany, Long id) {
        Company company = getCompanyById(id);
        company.setDescription(updatedCompany.getDescription());
        company.setName(updatedCompany.getName());
        return companyRepository.save(company);
    }


}
