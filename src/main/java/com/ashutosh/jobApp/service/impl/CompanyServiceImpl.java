package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.dto.CompanyProfileRequest;
import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.entity.User;
import com.ashutosh.jobApp.repository.CompanyRepository;
import com.ashutosh.jobApp.repository.UserRepository;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Company getAuthenticatedCompany(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return companyRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
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

    @Override
    @Transactional
    public Company updateCompanyProfile(CompanyProfileRequest request) {
        Company company = getAuthenticatedCompany();

        company.setName(request.getName());
        company.setDescription(request.getDescription());

        return companyRepository.save(company);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteCompany() {
        Company company = getAuthenticatedCompany();
        company.setActive(false);
        companyRepository.save(company);
    }


}
