package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.dto.request.ReviewRequestDto;
import com.ashutosh.jobApp.dto.response.ReviewResponseDto;
import com.ashutosh.jobApp.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/companies/{companyId}/reviews")
    public ResponseEntity<Page<ReviewResponseDto>> getAllReviews(@PathVariable Long companyId,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "id") String sortBy,
                                                                 @RequestParam(defaultValue = "asc") String sortDir){

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ?Sort.by(sortBy).descending()
                :Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page ,size , sort);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.getAllReviews(companyId , pageable));
    }

    @PostMapping("/companies/{companyId}/reviews")
    @PreAuthorize("hasRole('APPLICANT')")
    public  ResponseEntity<ReviewResponseDto> postReview(@PathVariable Long companyId ,
                                             @Valid @RequestBody ReviewRequestDto requestDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.postReview(companyId , requestDto));
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long reviewId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.getReviewById(reviewId));
    }

    @PutMapping("/reviews/{reviewId}")
    @PreAuthorize("hasRole('APPLICANT')")
    public ResponseEntity<ReviewResponseDto> updateReviewById(@PathVariable Long reviewId,
                                                   @Valid @RequestBody ReviewRequestDto requestDto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.updateReviewById(reviewId , requestDto));
    }

    @DeleteMapping("/reviews/{reviewId}")
    @PreAuthorize("hasAnyRole('APPLICANT' , 'ADMIN')")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
