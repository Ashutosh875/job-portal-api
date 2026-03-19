package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.repository.ApplicantRepository;
import com.ashutosh.jobApp.repository.JobRepository;
import com.ashutosh.jobApp.service.ApplicantService;
import com.ashutosh.jobApp.service.ApplicationService;
import com.ashutosh.jobApp.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicantService applicantService;
    private final JobService jobService;
    private final JobRepository jobRepository;
    private final ApplicantRepository applicantRepository;


    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.REPEATABLE_READ
    )
    @Override
    public Job applyToJob(Long applicantId, Long jobId) {

        Applicant applicant = applicantService.getApplicantById(applicantId);
        Job job = jobService.findJobById(jobId);

        job.addApplicant(applicant);

        return jobRepository.save(job);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.REPEATABLE_READ
    )
    @Override
    public void withdrawApplication(Long applicantId, Long jobId) {

        Applicant applicant = applicantService.getApplicantById(applicantId);
        Job job = jobService.findJobById(jobId);

        job.removeApplicant(applicant);
        jobRepository.save(job);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Page<Job> getJobsForApplicant(Long applicantId, Pageable pageable) {

        return jobRepository.findJobsByApplicantId(applicantId , pageable);
    }

    @Override
    public Page<Applicant> getApplicantForJob(Long jobId, Pageable pageable) {

        return applicantRepository.findApplicantsByJobId(jobId , pageable);
    }


}
