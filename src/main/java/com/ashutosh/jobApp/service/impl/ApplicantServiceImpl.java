package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.dto.ApplicantProfileRequest;
import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.User;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.repository.ApplicantRepository;
import com.ashutosh.jobApp.repository.UserRepository;
import com.ashutosh.jobApp.service.ApplicantService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;

    public Applicant getAuthenticatedApplicant(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return applicantRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Applicant Not Found"));
    }

    @Transactional
    public Applicant updateApplicantProfile(ApplicantProfileRequest applicantProfileRequest){

        Applicant applicant = getAuthenticatedApplicant();

        applicant.setName(applicantProfileRequest.getName());
        applicant.setExperience(applicantProfileRequest.getExperience());
        applicant.setJobTitle(applicantProfileRequest.getJobTitle());

        return applicantRepository.save(applicant);
    }

    @Override
    @Transactional(readOnly = true)
    public Applicant getApplicantById(Long applicantId) {
        return applicantRepository.findById(applicantId)
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
