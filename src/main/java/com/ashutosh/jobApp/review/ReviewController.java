package com.ashutosh.jobApp.review;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies/{companyId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long companyId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.getAllReviews(companyId));
    }

    @PostMapping
    public  ResponseEntity<Review> addReview(@PathVariable Long companyId ,
                                             @RequestBody Review review){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.addReview(companyId , review));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long companyId ,
                                                @PathVariable Long reviewId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.getReviewById(companyId , reviewId));
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReviewById(@PathVariable Long companyId ,
                                                   @PathVariable Long reviewId,
                                                   @RequestBody Review review){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewService.updateReviewById(companyId , reviewId , review));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long companyId ,
                                         @PathVariable Long reviewId){
        reviewService.deleteReview(companyId , reviewId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
