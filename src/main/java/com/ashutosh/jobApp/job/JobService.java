package com.ashutosh.jobApp.job;

import java.util.List;

public interface JobService {

    List<Job> findAll();
    Job createJob(Job job);

    Job findJobById(Long id);

    void deleteJobById(Long id);

    Job updateJobById(Job job, Long id);
}
