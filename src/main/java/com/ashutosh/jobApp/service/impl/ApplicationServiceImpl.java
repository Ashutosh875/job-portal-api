package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.repository.ApplicantRepository;
import com.ashutosh.jobApp.repository.JobRepository;
import com.ashutosh.jobApp.service.ApplicantService;
import com.ashutosh.jobApp.service.ApplicationService;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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
    private final CompanyService companyService;


    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.REPEATABLE_READ
    )
    @Override
    public Job applyToJob(Long jobId) {

        Applicant applicant = applicantService.getAuthenticatedApplicant();
        Job job = jobService.findJobById(jobId);

        job.addApplicant(applicant);

        return jobRepository.save(job);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.REPEATABLE_READ
    )
    @Override
    public void withdrawApplication(Long jobId) {

        Applicant applicant = applicantService.getAuthenticatedApplicant();
        Job job = jobService.findJobById(jobId);

        if(!job.getApplicants().contains(applicant)){
            throw new ResourceNotFoundException("No Application Found");
        }

        job.removeApplicant(applicant);
        jobRepository.save(job);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Page<Job> getApplicationsOfApplicant(Pageable pageable) {
        Applicant applicant = applicantService.getAuthenticatedApplicant();

        return jobRepository.findJobsByApplicantId(applicant.getId() , pageable);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Page<Applicant> getApplicantForJob(Long jobId, Pageable pageable) {

        Company company = companyService.getAuthenticatedCompany();
        Job job = jobService.findJobById(jobId);

        if(!company.getId().equals(job.getCompany().getId())){
            throw new AccessDeniedException("Access Denied");
        }

        return applicantRepository.findApplicantsByJobId(jobId , pageable);
    }


}
