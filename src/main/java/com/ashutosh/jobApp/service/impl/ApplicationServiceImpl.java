package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.repository.JobRepository;
import com.ashutosh.jobApp.service.ApplicantService;
import com.ashutosh.jobApp.service.ApplicationService;
import com.ashutosh.jobApp.service.JobService;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicantService applicantService;
    private final JobService jobService;
    private final JobRepository jobRepository;

    public ApplicationServiceImpl(ApplicantService applicantService, JobService jobService, JobRepository jobRepository) {
        this.applicantService = applicantService;
        this.jobService = jobService;
        this.jobRepository = jobRepository;
    }

    @Override
    public Job applyToJob(Long applicantId, Long jobId) {

        Applicant applicant = applicantService.getApplicantById(applicantId);
        Job job = jobService.findJobById(jobId);

        job.addApplicant(applicant);

        return jobRepository.save(job);
    }

    @Override
    public void withdrawApplication(Long applicantId, Long jobId) {

        Applicant applicant = applicantService.getApplicantById(applicantId);
        Job job = jobService.findJobById(jobId);

        job.removeApplicant(applicant);
        jobRepository.save(job);
    }


}
