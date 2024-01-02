package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.ReviewDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.WishlistDto;
import com.holisticbabe.holisticbabemarketplace.Models.Review;
import com.holisticbabe.holisticbabemarketplace.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    @GetMapping("/{reviewId}")
    public Review getReview(@PathVariable Long reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReviews(@RequestBody ReviewDto reviewDto) throws Exception {

        ReviewDto result = reviewService.giveReview(reviewDto);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the review");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{reviewId}")
    public Review updateReview(@PathVariable Long reviewId, @RequestBody Review updatedReview) {
        return reviewService.updateReview(reviewId, updatedReview);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

   @GetMapping("/countReviews")
    public   int countReviews(@PathVariable Long productId){
      return  reviewService.countReviews(productId);
   }

    @GetMapping("/{productId}/rating")
    public ResponseEntity<Double> getProductRating(@PathVariable Long productId) {
        double productRating = reviewService.calculateProductRating(productId);
        if (productRating >= 0.0) {
            return ResponseEntity.ok(productRating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{productId}/reviews")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        if (reviews.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(reviews);
        }
    }
    @GetMapping("/by-product/{productId}")
    public ResponseEntity<List<ReviewDto>> getAllReviewsByProductId(@PathVariable Long productId) {
        List<ReviewDto> reviews = reviewService.getAllReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }



    @GetMapping("/{productId}/reviews/average-rating")
    public ResponseEntity<Double> averageRatingByProduct(@PathVariable Long productId) {
        Double averageRating = reviewService.averageRatingByProduct(productId);
        return ResponseEntity.ok(averageRating);
    }
    @GetMapping("/sum-rating-values")
    public ResponseEntity<Double> sumRatingValues() {
        Double sum = reviewService.sumRatingValues();
        return ResponseEntity.ok(sum);
    }


}
