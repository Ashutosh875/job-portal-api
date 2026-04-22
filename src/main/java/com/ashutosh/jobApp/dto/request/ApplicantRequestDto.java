package com.ashutosh.jobApp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicantRequestDto {

    @NotBlank(message = "name is required")
    private String name;

    @Min(value = 0 , message = "experience can not be negative")
    private Integer experience;

    @NotBlank(message = "job title is required")
    private String jobTitle;

}
