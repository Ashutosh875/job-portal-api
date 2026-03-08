package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.entity.Applicant;



public interface ApplicantService {
    Applicant createApplicant(Applicant applicant);

    Applicant getApplicantById(Long applicantId);

    Applicant updateApplicant(Applicant updatedApplicant, Long applicantId);

    void deleteApplicant(Long applicantId);
}
