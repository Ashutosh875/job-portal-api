package com.ashutosh.jobApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantProfileRequest {

    private String name;
    private Integer experience;
    private String jobTitle;

}
