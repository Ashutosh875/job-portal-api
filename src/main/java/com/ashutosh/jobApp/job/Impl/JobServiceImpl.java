package com.ashutosh.jobApp.job.Impl;

import com.ashutosh.jobApp.company.Company;
import com.ashutosh.jobApp.company.CompanyRepository;
import com.ashutosh.jobApp.job.Job;
import com.ashutosh.jobApp.job.JobRepository;
import com.ashutosh.jobApp.job.JobService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;

    public JobServiceImpl(JobRepository jobRepository, CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public Job createJob(Job job) {
        Long companyId = job.getCompany().getId();

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        job.setCompany(company);
        Job createdJob = jobRepository.save(job);
        return createdJob;
    }

    @Override
    public Job findJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job with id : " + id + " Not found"));
    }

    @Override
    public void deleteJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job with id : " + id + " Not found"));
        jobRepository.delete(job);
    }

    @Override
    public Job updateJobById(Job updatedjob, Long id) {

        Long companyId = updatedjob.getCompany().getId();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job with id : " + id + " Not found"));
            job.setDescription(updatedjob.getDescription());
            job.setLocation(updatedjob.getLocation());
            job.setTitle(updatedjob.getTitle());
            job.setMaxSalary(updatedjob.getMaxSalary());
            job.setMinSalary(updatedjob.getMinSalary());
            job.setCompany(company);
            return jobRepository.save(job);
    }


}
