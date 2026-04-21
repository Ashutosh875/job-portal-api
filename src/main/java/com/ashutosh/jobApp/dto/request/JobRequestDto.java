package com.ashutosh.jobApp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequestDto {

    private String title;
    private String description;
    private Long minSalary;
    private Long maxSalary;
    private String location;

}
