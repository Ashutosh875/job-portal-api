package com.ashutosh.jobApp.service;


import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {

    Job applyToJob(Long jobId);

    void withdrawApplication(Long jobId);


    Page<Job> getApplicationsOfApplicant(Pageable pageable);

    Page<Applicant> getApplicantForJob(Long jobId, Pageable pageable);
}
