package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.repository.ApplicantRepository;
import com.ashutosh.jobApp.service.ApplicantService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;



@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantServiceImpl(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Override
    public Applicant createApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }


    @Override
    public Applicant getApplicantById(Long applicantId) {
        return applicantRepository.findById(applicantId)
                .orElseThrow(() -> new ResourceNotFoundException("No applicant found with id : " + applicantId));
    }

    @Transactional
    @Override
    public Applicant updateApplicant(Applicant updatedApplicant, Long applicantId) {

        Applicant applicant = getApplicantById(applicantId);

        applicant.setName(updatedApplicant.getName());
        applicant.setExperience(updatedApplicant.getExperience());
        applicant.setJobTitle(updatedApplicant.getJobTitle());

        return applicantRepository.save(applicant);
    }

    @Transactional
    @Override
    public void deleteApplicant(Long applicantId){
        Applicant applicant = getApplicantById(applicantId);
        applicantRepository.delete(applicant);
    }
}
