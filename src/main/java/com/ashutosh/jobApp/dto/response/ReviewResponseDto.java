package com.ashutosh.jobApp.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {

    private Long reviewId;
    private String applicantName;
    private String companyName;
    private String title;
    private String description;
    private Double rating;

}
