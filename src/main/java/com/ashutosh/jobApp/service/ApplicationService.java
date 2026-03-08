package com.ashutosh.jobApp.service;


import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.Job;

public interface ApplicationService {

    Job applyToJob(Long applicantId, Long jobId);

    void withdrawApplication(Long applicantId, Long jobId);
}
