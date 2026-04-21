package com.ashutosh.jobApp.mapper;

import com.ashutosh.jobApp.dto.request.JobRequestDto;
import com.ashutosh.jobApp.dto.response.JobResponseDto;
import com.ashutosh.jobApp.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public Job toEntity(JobRequestDto requestDto){

        Job job = new Job();

        job.setTitle(requestDto.getTitle());
        job.setDescription(requestDto.getDescription());
        job.setMinSalary(requestDto.getMinSalary());
        job.setMaxSalary(requestDto.getMaxSalary());
        job.setLocation(requestDto.getLocation());

        return job;
    }

    public JobResponseDto toResponse(Job job){

        JobResponseDto dto = new JobResponseDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setMinSalary(job.getMinSalary());
        dto.setMaxSalary(job.getMaxSalary());
        dto.setLocation(job.getLocation());
        dto.setCompanyId(job.getCompany().getId());
        dto.setCompanyName(job.getCompany().getName());
        dto.setApplicantCount(job.getApplicants().size());

        return dto;
    }
}
