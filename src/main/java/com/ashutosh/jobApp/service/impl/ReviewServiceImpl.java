package com.ashutosh.jobApp.service.impl;

import com.ashutosh.jobApp.dto.request.ReviewRequestDto;
import com.ashutosh.jobApp.dto.response.ReviewResponseDto;
import com.ashutosh.jobApp.entity.Applicant;
import com.ashutosh.jobApp.entity.Company;
import com.ashutosh.jobApp.mapper.ReviewMapper;
import com.ashutosh.jobApp.repository.CompanyRepository;
import com.ashutosh.jobApp.service.ApplicantService;
import com.ashutosh.jobApp.exception.ResourceNotFoundException;
import com.ashutosh.jobApp.entity.Review;
import com.ashutosh.jobApp.repository.ReviewRepository;
import com.ashutosh.jobApp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ApplicantService applicantService;
    private final CompanyRepository companyRepository;
    private final ReviewMapper reviewMapper;

    // Helper method to find company by id
    private Company getCompanyById(Long companyId){
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("company with id : " + companyId + " Not found"));
    }

    //helper method to find review by id
    private Review findReviewById(Long reviewId){
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("No review Found"));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<ReviewResponseDto> getAllReviews(Long companyId , Pageable pageable) {

        return reviewRepository
                .findByCompanyId(companyId , pageable)
                .map(reviewMapper::toResponseDto);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public ReviewResponseDto postReview(Long companyId, ReviewRequestDto requestDto) {

        Applicant applicant = applicantService.getAuthenticatedApplicant();
        Review review = reviewMapper.toEntity(requestDto);
        review.setApplicant(applicant);
        Company company = getCompanyById(companyId);
        company.addReview(review);

        return reviewMapper.toResponseDto(reviewRepository.save(review));
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponseDto getReviewById(Long reviewId) {
        return reviewMapper.toResponseDto(findReviewById(reviewId));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public ReviewResponseDto updateReviewById(Long reviewId, ReviewRequestDto requestDto) {

        Applicant applicant = applicantService.getAuthenticatedApplicant();

        Review review = findReviewById(reviewId);

        if(!applicant.getId().equals(review.getApplicant().getId())){
            throw new AccessDeniedException("No Access To Update this Review");
        }

        review.setDescription(requestDto.getDescription());
        review.setRating(requestDto.getRating());
        review.setTitle(requestDto.getTitle());

        return reviewMapper.toResponseDto(reviewRepository.save(review));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Override
    public void deleteReview(Long reviewId) {

        Applicant applicant = applicantService.getAuthenticatedApplicant();

        Review review = findReviewById(reviewId);

        if(!applicant.getId().equals(review.getApplicant().getId())){
            throw new AccessDeniedException("No Access To Delete this Review");
        }

        reviewRepository.delete(review);
    }

}
