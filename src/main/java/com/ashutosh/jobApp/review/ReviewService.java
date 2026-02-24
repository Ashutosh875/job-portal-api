package com.ashutosh.jobApp.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);
    Review addReview(Long companyId , Review review);

    Review getReviewById(Long companyId, Long id);

    Review updateReviewById(Long companyId, Long reviewId, Review review);

    void deleteReview(Long companyId, Long reviewId);
}
