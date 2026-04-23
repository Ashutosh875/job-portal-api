package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.dto.request.CompanyRequestDto;
import com.ashutosh.jobApp.dto.response.CompanyResponseDto;
import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.entity.User;
import com.ashutosh.jobApp.mapper.CompanyMapper;
import com.ashutosh.jobApp.repository.CompanyRepository;
import com.ashutosh.jobApp.repository.UserRepository;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMapper companyMapper;

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
    public Page<CompanyResponseDto> getAllCompanies(Pageable pageable) {

        log.info("request to fetch all the companies");

        return companyRepository
                .findAll(pageable)
                .map(companyMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyResponseDto getCompanyById(Long id) {

        log.info("fetching Company with Id : {}", id);

        return companyRepository.findById(id)
                .map(companyMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("company with id : " + id + " Not found"));
    }

    @Override
    @Transactional
    public CompanyResponseDto updateCompanyProfile(CompanyRequestDto request) {
        Company company = getAuthenticatedCompany();

        log.info("Company with email: {} trying to update profile",company.getUser().getEmail());

        company.setName(request.getName());
        company.setDescription(request.getDescription());

        Company updatedCompanyProfile = companyRepository.save(company);

        log.info("Company with email: {} successfully updated profile",company.getUser().getEmail());

        return companyMapper
                .toResponseDto(updatedCompanyProfile);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteCompany() {
        Company company = getAuthenticatedCompany();

        log.info("Company with email: {} requested account deactivation", company.getUser().getEmail());
        company.setActive(false);
        log.info("Company with email: {} successfully deactivated", company.getUser().getEmail());
        companyRepository.save(company);
    }


}
