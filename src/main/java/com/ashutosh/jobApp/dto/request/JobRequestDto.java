package com.ashutosh.jobApp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobRequestDto {

    @NotBlank(message = "job title is required")
    private String title;

    @NotBlank(message = "description is required")
    @Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
    private String description;

    @NotNull(message = "this is required")
    @Min(value = 0 , message = "Salary can not be negative")
    private Long minSalary;

    @NotNull(message = "this is required")
    @Min(value = 0 , message = "Salary can not be negative")
    private Long maxSalary;

    @NotBlank(message = "location is required")
    private String location;

}
