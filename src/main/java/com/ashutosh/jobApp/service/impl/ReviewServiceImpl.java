package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.entity.Review;
import com.ashutosh.jobApp.repository.ReviewRepository;
import com.ashutosh.jobApp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    @Transactional(readOnly = true)
    @Override
    public Page<Review> getAllReviews(Long companyId , Pageable pageable) {

        return reviewRepository.findByCompanyId(companyId , pageable);
    }

    @Transactional
    @Override
    public Review postReview(Long companyId, Review review) {

        Company company = companyService.getCompanyById(companyId);
        company.addReview(review);

        return reviewRepository.save(review);
    }

    @Override
    @Transactional(readOnly = true)
    public Review getReviewById(Long reviewId) {

        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("No review Found"));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public Review updateReviewById(Long reviewId, Review updatedReview) {

        Review review = getReviewById(reviewId);

        review.setDescription(updatedReview.getDescription());
        review.setRating(updatedReview.getRating());
        review.setTitle(updatedReview.getTitle());

        return reviewRepository.save(review);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteReview(Long reviewId) {
        Review review = getReviewById(reviewId);
        reviewRepository.delete(review);
    }

}
