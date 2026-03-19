package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewService {

    Page<Review> getAllReviews(Long companyId , Pageable pageable);

    Review postReview(Long companyId , Review review);

    Review getReviewById(Long id);

    Review updateReviewById(Long reviewId, Review review);

    void deleteReview(Long reviewId);
}
