package com.ashutosh.jobApp.mapper;

import com.ashutosh.jobApp.dto.response.CompanyResponseDto;
import com.ashutosh.jobApp.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyResponseDto toResponseDto(Company company){

        CompanyResponseDto responseDto = new CompanyResponseDto();
        responseDto.setId(company.getId());
        responseDto.setName(company.getName());
        responseDto.setDescription(company.getDescription());
        responseDto.setJobCount(company.getJobs().size());
        responseDto.setReviewCount(company.getReviews().size());

        return responseDto;
    }
}
