package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.dto.response.ApplicantResponseDto;
import com.ashutosh.jobApp.dto.response.JobResponseDto;
import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.exception.DuplicateApplicationException;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.mapper.ApplicantMapper;
import com.ashutosh.jobApp.mapper.JobMapper;
import com.ashutosh.jobApp.repository.ApplicantRepository;
import com.ashutosh.jobApp.repository.JobRepository;
import com.ashutosh.jobApp.service.ApplicantService;
import com.ashutosh.jobApp.service.ApplicationService;
import com.ashutosh.jobApp.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicantService applicantService;
    private final JobRepository jobRepository;
    private final ApplicantRepository applicantRepository;
    private final CompanyService companyService;
    private final JobMapper jobMapper;
    private final ApplicantMapper applicantMapper;

    //helper method to get job entity by id
    private Job getJobById(Long jobId){
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job with id : " + jobId + " Not found"));
    }


    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.REPEATABLE_READ
    )
    @Override
    public JobResponseDto applyToJob(Long jobId) {

        Applicant applicant = applicantService.getAuthenticatedApplicant();
        Job job = getJobById(jobId);

        if(job.getApplicants().contains(applicant)){
            log.warn("Applicant {} attempted to apply to same job {} again" , applicant.getUser().getEmail() ,jobId);
            throw new DuplicateApplicationException("You have already applied to this job");
        }

        log.info("Applicant with email: {} requested to apply for job with id : {}", applicant.getUser().getEmail(),jobId);

        job.addApplicant(applicant);
        Job appliedJob = jobRepository.save(job);
        log.info("Applicant with email: {} successfully applied for job with id : {}", applicant.getUser().getEmail(),jobId);
        return jobMapper.toResponse(appliedJob);
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            isolation = Isolation.REPEATABLE_READ
    )
    @Override
    public void withdrawApplication(Long jobId) {

        Applicant applicant = applicantService.getAuthenticatedApplicant();
        Job job = getJobById(jobId);
        log.info("Applicant with email: {} requested to withdraw application for job with id : {}", applicant.getUser().getEmail(),jobId);

        if(!job.getApplicants().contains(applicant)){
            log.warn("Applicant {} attempted to withdraw from job id {} but no application found", applicant.getUser().getEmail(), jobId);
            throw new ResourceNotFoundException("No Application Found");
        }

        job.removeApplicant(applicant);
        log.info("Applicant with email: {} successfully withdrawn application for job with id : {}", applicant.getUser().getEmail(),jobId);
        jobRepository.save(job);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Page<JobResponseDto> getApplicationsOfApplicant(Pageable pageable) {
        Applicant applicant = applicantService.getAuthenticatedApplicant();
        log.info("fetching the applications for applicant : {}",applicant.getUser().getEmail());
        return jobRepository
                .findJobsByApplicantId(applicant.getId() , pageable)
                .map(jobMapper::toResponse);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Page<ApplicantResponseDto> getApplicantForJob(Long jobId, Pageable pageable) {

        Company company = companyService.getAuthenticatedCompany();
        Job job = getJobById(jobId);
        log.info("fetching the applicants for job id : {}",jobId);
        if(!company.getId().equals(job.getCompany().getId())){
            log.warn("Unauthorized access to fetch applicants for job id {} by company {}",jobId,company.getUser().getEmail());
            throw new AccessDeniedException("Access Denied");
        }

        return applicantRepository
                .findApplicantsByJobId(jobId , pageable)
                .map(applicantMapper::toResponseDto);
    }


}
