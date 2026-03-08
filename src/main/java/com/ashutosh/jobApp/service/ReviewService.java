package com.ashutosh.jobApp.service;

import com.ashutosh.jobApp.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);

    Review postReview(Long companyId , Review review);

    Review getReviewById(Long id);

    Review updateReviewById(Long reviewId, Review review);

    void deleteReview(Long reviewId);
}
