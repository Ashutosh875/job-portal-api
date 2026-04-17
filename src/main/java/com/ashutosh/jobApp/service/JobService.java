package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    Job postJob(Job job);

    Page<Job> findAllJobs(Pageable pageable);

    Job findJobById(Long jobId);

    List<Job> findAllJobsByCompany(Long companyId);

    Job updateJobById(Job job , Long jobId);

    void deleteJobById(Long jobId);

    Page<Job> findJobByLocation(String location, Pageable pageable);
    Page<Job> findJobByMinSalary(Long minSalary, Pageable pageable);
    Page<Job> findJobByTitle(String title, Pageable pageable);
}
