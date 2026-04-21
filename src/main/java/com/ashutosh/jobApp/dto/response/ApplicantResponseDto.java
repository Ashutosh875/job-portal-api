package com.ashutosh.jobApp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantResponseDto {

    private String email;
    private String name;
    private Integer experience;
    private String jobTitle;

}
