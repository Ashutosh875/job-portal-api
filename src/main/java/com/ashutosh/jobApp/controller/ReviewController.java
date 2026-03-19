package com.ashutosh.jobApp.controller;

import com.ashutosh.jobApp.entity.Review;
import com.ashutosh.jobApp.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/companies/{companyId}/reviews")
    public ResponseEntity<Page<Review>> getAllReviews(@PathVariable Long companyId,
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
    public  ResponseEntity<Review> postReview(@PathVariable Long companyId ,
                                             @RequestBody Review review){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.postReview(companyId , review));
    }

    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.getReviewById(reviewId));
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReviewById(@PathVariable Long reviewId,
                                                   @RequestBody Review review){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.updateReviewById(reviewId , review));
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
