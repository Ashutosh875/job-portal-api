package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.repository.CompanyRepository;
import com.ashutosh.jobApp.entity.Job;
import com.ashutosh.jobApp.repository.JobRepository;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.service.JobService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyService companyService;

    public JobServiceImpl(JobRepository jobRepository, CompanyService companyService, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyService = companyService;
    }

    @Transactional
    @Override
    public Job createJob(Job job , Long companyId) {

        Company company = companyService.getCompanyById(companyId);
        company.addJob(job);

        return jobRepository.save(job);
    }

    @Override
    public Page<Job> findAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    public Job findJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job with id : " + id + " Not found"));
    }


    @Override
    public List<Job> findAllJobsByCompany(Long companyId) {
        return jobRepository.findAllByCompanyId(companyId);
    }

    @Transactional
    @Override
    public Job updateJobById(Job updatedjob , Long jobId) {

        Job job = findJobById(jobId);

        job.setDescription(updatedjob.getDescription());
        job.setLocation(updatedjob.getLocation());
        job.setTitle(updatedjob.getTitle());
        job.setMinSalary(updatedjob.getMinSalary());
        job.setMaxSalary(updatedjob.getMaxSalary());

        return jobRepository.save(job);
    }

    @Transactional
    @Override
    public void deleteJobById(Long jobId) {
        Job job = findJobById(jobId);
        jobRepository.delete(job);
    }

    @Override
    public Page<Job> findJobByLocation(String location , Pageable pageable) {
        return jobRepository.findJobsByLocation(location,pageable);
    }

    @Override
    public Page<Job> findJobByMinSalary(Long minSalary , Pageable pageable) {
        return jobRepository.findJobsByMinSalary(minSalary,pageable);
    }

    @Override
    public Page<Job> findJobByTitle(String title,Pageable pageable) {
        return jobRepository.findJobsByTitle(title,pageable);
    }


}
