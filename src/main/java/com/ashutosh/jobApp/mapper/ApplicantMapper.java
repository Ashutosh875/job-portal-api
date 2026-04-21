package com.ashutosh.jobApp.mapper;

import com.ashutosh.jobApp.dto.response.ApplicantResponseDto;
import com.ashutosh.jobApp.entity.Applicant;
import org.springframework.stereotype.Component;

@Component
public class ApplicantMapper {

    public ApplicantResponseDto toResponseDto(Applicant applicant){

        ApplicantResponseDto responseDto = new ApplicantResponseDto();

        responseDto.setEmail(applicant.getUser().getEmail());
        responseDto.setName(applicant.getName());
        responseDto.setExperience(applicant.getExperience());
        responseDto.setJobTitle(applicant.getJobTitle());

        return  responseDto;
    }
}
