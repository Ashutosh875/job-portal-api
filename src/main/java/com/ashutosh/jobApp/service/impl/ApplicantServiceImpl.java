package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.dto.request.ApplicantRequestDto;
import com.ashutosh.jobApp.dto.response.ApplicantResponseDto;
import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.User;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.mapper.ApplicantMapper;
import com.ashutosh.jobApp.repository.ApplicantRepository;
import com.ashutosh.jobApp.repository.UserRepository;
import com.ashutosh.jobApp.service.ApplicantService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;
    private final ApplicantMapper applicantMapper;

    public Applicant getAuthenticatedApplicant(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return applicantRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Applicant Not Found"));
    }

    @Override
    @Transactional
    public ApplicantResponseDto updateApplicantProfile(ApplicantRequestDto applicantRequestDto){

        Applicant applicant = getAuthenticatedApplicant();

        applicant.setName(applicantRequestDto.getName());
        applicant.setExperience(applicantRequestDto.getExperience());
        applicant.setJobTitle(applicantRequestDto.getJobTitle());

        return applicantMapper
                .toResponseDto(applicantRepository.save(applicant));
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicantResponseDto getApplicantById(Long applicantId) {
        return applicantRepository.findById(applicantId)
                .map(applicantMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("No applicant found with id : " + applicantId));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteApplicant(){
        Applicant applicant = getAuthenticatedApplicant();
        applicant.setActive(false);
        applicantRepository.save(applicant);
    }
}
