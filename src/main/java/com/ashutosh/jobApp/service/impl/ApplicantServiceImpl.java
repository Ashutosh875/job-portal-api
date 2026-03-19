package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.repository.ApplicantRepository;
import com.ashutosh.jobApp.service.ApplicantService;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    @Override
    @Transactional
    public Applicant createApplicant(Applicant applicant) {
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
    public Applicant updateApplicant(Applicant updatedApplicant, Long applicantId) {

        Applicant applicant = getApplicantById(applicantId);

        applicant.setName(updatedApplicant.getName());
        applicant.setExperience(updatedApplicant.getExperience());
        applicant.setJobTitle(updatedApplicant.getJobTitle());

        return applicantRepository.save(applicant);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteApplicant(Long applicantId){
        Applicant applicant = getApplicantById(applicantId);
        applicant.setActive(false);
        applicantRepository.save(applicant);
    }
}
