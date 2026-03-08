package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.entity.Job;

import java.util.List;

public interface JobService {

    Job createJob(Job job , Long companyId);

    List<Job> findAllJobs();

    Job findJobById(Long jobId);

    List<Job> findAllJobsByCompany(Long companyId);

    Job updateJobById(Job job , Long jobId);

    void deleteJobById(Long jobId);
}
