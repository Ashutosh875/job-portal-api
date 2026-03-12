package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.service.CompanyService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.entity.Review;
import com.ashutosh.jobApp.repository.ReviewRepository;
import com.ashutosh.jobApp.service.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyService companyService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyService companyService) {
        this.reviewRepository = reviewRepository;
        this.companyService = companyService;
    }

    @Override
    public List<Review> getAllReviews(Long companyId) {
        Optional<List<Review>> reviews = Optional.ofNullable(reviewRepository.findByCompanyId(companyId));
        return reviews
                .orElseThrow(() -> new ResourceNotFoundException("No review Found"));
    }

    @Transactional
    @Override
    public Review postReview(Long companyId, Review review) {

        Company company = companyService.getCompanyById(companyId);
        company.addReview(review);

        return reviewRepository.save(review);
    }

    @Override
    public Review getReviewById(Long reviewId) {

        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("No review Found"));
    }

    @Transactional
    @Override
    public Review updateReviewById(Long reviewId, Review updatedReview) {

        Review review = getReviewById(reviewId);

        review.setDescription(updatedReview.getDescription());
        review.setRating(updatedReview.getRating());
        review.setTitle(updatedReview.getTitle());

        return reviewRepository.save(review);
    }

    @Transactional
    @Override
    public void deleteReview(Long reviewId) {
        Review review = getReviewById(reviewId);
        reviewRepository.delete(review);
    }

}
