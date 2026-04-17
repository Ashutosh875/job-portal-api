package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.dto.ApplicantProfileRequest;
import com.ashutosh.jobApp.entity.Applicant;



public interface ApplicantService {

    Applicant getAuthenticatedApplicant();

    Applicant getApplicantById(Long applicantId);

    void deleteApplicant();

    Applicant updateApplicantProfile(ApplicantProfileRequest applicantProfileRequest);
}
