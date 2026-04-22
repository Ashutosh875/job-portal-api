package com.ashutosh.jobApp.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCompanyDto {

    @NotBlank(message = "email is required")
    @Email(message = "invalid email format")
    private String email;

    @NotBlank(message = "password is required")
    @Size(min = 6 , message = "password must be at least 6 characters")
    private String password;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    @Size(min = 10, max = 200, message = "Description must be between 10 and 200 characters")
    private String description;

}
