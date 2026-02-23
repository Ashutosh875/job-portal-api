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


}
