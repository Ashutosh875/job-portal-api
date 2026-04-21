package com.ashutosh.jobApp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    private String title;
    private String description;
    private Double rating;

}
