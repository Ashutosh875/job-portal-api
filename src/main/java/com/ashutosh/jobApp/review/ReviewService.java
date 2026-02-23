package com.ashutosh.jobApp.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);
    Review addReview(Long companyId , Review review);
}
