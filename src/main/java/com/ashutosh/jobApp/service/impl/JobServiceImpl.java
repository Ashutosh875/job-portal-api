package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.dto.request.JobRequestDto;
import com.ashutosh.jobApp.dto.response.JobResponseDto;
import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.mapper.JobMapper;
import com.ashutosh.jobApp.repository.JobRepository;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.service.JobService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.specification.JobSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyService companyService;
    private final JobMapper jobMapper;

    //helper method to get job entity by id
    private Job getJobById(Long jobId){
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job with id : " + jobId + " Not found"));
    }


    @Override
    @Transactional
    public JobResponseDto postJob(JobRequestDto jobRequestDto) {

        Company company = companyService.getAuthenticatedCompany();

        Job job = jobMapper.toEntity(jobRequestDto);

        company.addJob(job);

        return jobMapper
                .toResponse(jobRepository.save(job));
    }

    @Override
    @Transactional
    public Page<JobResponseDto> searchJobs(String location,
                                           Long minSalary,
                                           Long maxSalary,
                                           String title,
                                           Pageable pageable){

        Specification<Job> spec = (root, query, cb) -> cb.conjunction();

        if( location != null) spec = spec.and(JobSpecification.hasLocation(location));
        if( minSalary != null) spec = spec.and(JobSpecification.hasMinSalary(minSalary));
        if( maxSalary != null) spec = spec.and(JobSpecification.hasMaxSalary(maxSalary));
        if( title != null) spec = spec.and(JobSpecification.hasTitle(title));

        return jobRepository
                .findAll(spec , pageable)
                .map(jobMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public JobResponseDto findJobById(Long id) {
        return jobRepository.findById(id)
                .map(jobMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Job with id : " + id + " Not found"));
    }


    @Override
    @Transactional(readOnly = true)
    public Page<JobResponseDto> findAllJobsByCompany(Long companyId , Pageable pageable) {
        return jobRepository
                .findAllByCompanyId(companyId , pageable)
                .map(jobMapper::toResponse);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public JobResponseDto updateJobById(JobRequestDto jobRequestDto , Long jobId) {

        Company company = companyService.getAuthenticatedCompany();

        Job job = getJobById(jobId);

        if(!job.getCompany().getId().equals(company.getId())) {
            throw new AccessDeniedException("Access Denied");
        }

        Job updatedjob = jobMapper.toEntity(jobRequestDto);

        job.setDescription(updatedjob.getDescription());
        job.setLocation(updatedjob.getLocation());
        job.setTitle(updatedjob.getTitle());
        job.setMinSalary(updatedjob.getMinSalary());
        job.setMaxSalary(updatedjob.getMaxSalary());

        return jobMapper
                .toResponse(jobRepository.save(job));

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteJobById(Long jobId) {

        Job job = getJobById(jobId);

        Company company = companyService.getAuthenticatedCompany();

        if(!job.getCompany().getId().equals(company.getId())) {
            throw new AccessDeniedException("Access Denied");
        }

        jobRepository.delete(job);
    }

}
