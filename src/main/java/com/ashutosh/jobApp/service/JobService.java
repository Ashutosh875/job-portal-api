package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.dto.request.JobRequestDto;
import com.ashutosh.jobApp.dto.response.JobResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface JobService {

    JobResponseDto postJob(JobRequestDto jobRequestDto);

    Page<JobResponseDto> searchJobs(String location, Long minSalary, Long maxSalary,String title, Pageable pageable);

    JobResponseDto findJobById(Long jobId);

    Page<JobResponseDto> findAllJobsByCompany(Long companyId , Pageable pageable);

    JobResponseDto updateJobById(JobRequestDto jobRequestDto , Long jobId);

    void deleteJobById(Long jobId);

}
