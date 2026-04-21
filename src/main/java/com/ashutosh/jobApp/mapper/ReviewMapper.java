package com.ashutosh.jobApp.mapper;

import com.ashutosh.jobApp.dto.request.ReviewRequestDto;
import com.ashutosh.jobApp.dto.response.ReviewResponseDto;
import com.ashutosh.jobApp.entity.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewResponseDto toResponseDto(Review review){

        ReviewResponseDto dto = new ReviewResponseDto();

        dto.setReviewId(review.getId());
        dto.setTitle(review.getTitle());
        dto.setDescription(review.getDescription());
        dto.setRating(review.getRating());
        dto.setApplicantName(review.getApplicant().getName());
        dto.setCompanyName(review.getCompany().getName());

        return dto;
    }

    public Review toEntity(ReviewRequestDto dto) {

        Review review = new Review();

        review.setTitle(dto.getTitle());
        review.setDescription(dto.getDescription());
        review.setRating(dto.getRating());

        return review;
    }
}
