package com.ashutosh.jobApp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyResponseDto {

    private Long id;
    private String name;
    private String description;
    private int jobCount;
    private int reviewCount;

}
