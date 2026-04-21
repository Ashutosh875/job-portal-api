package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.dto.request.JobRequestDto;
import com.ashutosh.jobApp.dto.response.JobResponseDto;
import com.ashutosh.jobApp.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface JobService {

    JobResponseDto postJob(JobRequestDto jobRequestDto);

    Page<JobResponseDto> findAllJobs(Pageable pageable);

    JobResponseDto findJobById(Long jobId);

    Page<JobResponseDto> findAllJobsByCompany(Long companyId , Pageable pageable);

    JobResponseDto updateJobById(JobRequestDto jobRequestDto , Long jobId);

    void deleteJobById(Long jobId);

    Page<JobResponseDto> findJobByLocation(String location, Pageable pageable);
    Page<JobResponseDto> findJobByMinSalary(Long minSalary, Pageable pageable);
    Page<JobResponseDto> findJobByTitle(String title, Pageable pageable);
}
