package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.entity.User;
import com.ashutosh.jobApp.repository.CompanyRepository;
import com.ashutosh.jobApp.repository.JobRepository;
import com.ashutosh.jobApp.repository.UserRepository;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.service.JobService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyService companyService;


    @Override
    @Transactional
    public Job postJob(Job job) {

        Company company = companyService.getAuthenticatedCompany();

        company.addJob(job);

        return jobRepository.save(job);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Job> findAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Job findJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job with id : " + id + " Not found"));
    }


    @Override
    @Transactional(readOnly = true)
    public List<Job> findAllJobsByCompany(Long companyId) {
        return jobRepository.findAllByCompanyId(companyId);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Job updateJobById(Job updatedjob , Long jobId) {

        Company company = companyService.getAuthenticatedCompany();

        Job job = findJobById(jobId);

        if(!job.getCompany().getId().equals(company.getId())) {
            throw new AccessDeniedException("Access Denied");
        }

        job.setDescription(updatedjob.getDescription());
        job.setLocation(updatedjob.getLocation());
        job.setTitle(updatedjob.getTitle());
        job.setMinSalary(updatedjob.getMinSalary());
        job.setMaxSalary(updatedjob.getMaxSalary());

        return jobRepository.save(job);

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteJobById(Long jobId) {

        Job job = findJobById(jobId);
        Company company = companyService.getAuthenticatedCompany();

        if(!job.getCompany().getId().equals(company.getId())) {
            throw new AccessDeniedException("Access Denied");
        }

        jobRepository.delete(job);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Job> findJobByLocation(String location , Pageable pageable) {
        return jobRepository.findJobsByLocation(location,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Job> findJobByMinSalary(Long minSalary , Pageable pageable) {
        return jobRepository.findJobsByMinSalary(minSalary,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Job> findJobByTitle(String title,Pageable pageable) {
        return jobRepository.findJobsByTitle(title,pageable);
    }


}
