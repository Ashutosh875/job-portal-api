package com.ashutosh.jobApp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterApplicantDto {

    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6 , message = "password must be at least 6 characters")
    private String password;

    @NotBlank(message = "name is required")
    private String name;

    @Min(value = 0 , message = "experience can not be negative")
    private Integer experience;

    @NotBlank(message = "job title is required")
    private String jobTitle;

}
