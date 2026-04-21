package com.ashutosh.jobApp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobResponseDto {

    private Long id;
    private String title;
    private String description;
    private Long minSalary;
    private Long maxSalary;
    private String location;

    private Long companyId;
    private String companyName;

    private int applicantCount;
}
