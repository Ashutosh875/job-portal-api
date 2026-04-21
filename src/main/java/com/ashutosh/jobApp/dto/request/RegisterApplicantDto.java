package com.ashutosh.jobApp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterApplicantDto {

    private String email;
    private String password;
    private String name;
    private Integer experience;
    private String jobTitle;

}
