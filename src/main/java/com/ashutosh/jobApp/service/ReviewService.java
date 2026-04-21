package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.dto.request.ReviewRequestDto;
import com.ashutosh.jobApp.dto.response.ReviewResponseDto;
import com.ashutosh.jobApp.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    Page<ReviewResponseDto> getAllReviews(Long companyId , Pageable pageable);

    ReviewResponseDto postReview(Long companyId , ReviewRequestDto requestDto);

    ReviewResponseDto getReviewById(Long id);

    ReviewResponseDto updateReviewById(Long reviewId, ReviewRequestDto requestDto);

    void deleteReview(Long reviewId);
}
