package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.dto.request.ApplicantRequestDto;
import com.ashutosh.jobApp.dto.response.ApplicantResponseDto;
import com.ashutosh.jobApp.entity.Applicant;



public interface ApplicantService {

    Applicant getAuthenticatedApplicant();

    ApplicantResponseDto getApplicantById(Long applicantId);

    void deleteApplicant();

    ApplicantResponseDto updateApplicantProfile(ApplicantRequestDto applicantRequestDto);
}
