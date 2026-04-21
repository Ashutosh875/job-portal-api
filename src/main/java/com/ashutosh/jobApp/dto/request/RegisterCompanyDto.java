package com.ashutosh.jobApp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCompanyDto {

    private String email;
    private String password;
    private String name;
    private String description;

}
