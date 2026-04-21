package com.ashutosh.jobApp.service;


import com.ashutosh.jobApp.dto.response.ApplicantResponseDto;
import com.ashutosh.jobApp.dto.response.JobResponseDto;
import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {

    JobResponseDto applyToJob(Long jobId);

    void withdrawApplication(Long jobId);


    Page<JobResponseDto> getApplicationsOfApplicant(Pageable pageable);

    Page<ApplicantResponseDto> getApplicantForJob(Long jobId, Pageable pageable);
}
