package com.ashutosh.jobApp.review.impl;

import com.ashutosh.jobApp.company.Company;
import com.ashutosh.jobApp.company.CompanyService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.review.Review;
import com.ashutosh.jobApp.review.ReviewRepository;
import com.ashutosh.jobApp.review.ReviewService;
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

    @Override
    public Review addReview(Long companyId, Review review) {
        Optional<Company> company = Optional.ofNullable(companyService.getCompanyById(companyId));
        if (company.isPresent()){
            review.setCompany(company.get());
            return reviewRepository.save(review);
        } else throw new ResourceNotFoundException("No company found");

    }

    @Override
    public Review getReviewById(Long companyId, Long reviewId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        Optional<Review> review =  reviews
                                    .stream()
                                    .filter(r -> r.getId().equals(reviewId))
                                    .findFirst();
        return review
                .orElseThrow(() -> new ResourceNotFoundException("No review Found"));
    }

    @Override
    public Review updateReviewById(Long companyId, Long reviewId, Review updatedReview) {
        Review review = getReviewById(companyId , reviewId);
        review.setCompany(companyService.getCompanyById(companyId));
        review.setDescription(updatedReview.getDescription());
        review.setRating(updatedReview.getRating());
        review.setTitle(updatedReview.getTitle());

        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long companyId, Long reviewId) {
        Review review = getReviewById(companyId , reviewId);
        reviewRepository.delete(review);
    }

}
