package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Review;
import com.holisticbabe.holisticbabemarketplace.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> createReview(@RequestBody Review review) {
        try{
            reviewService.createReview(review);
            return ResponseEntity.ok("return ok");
        }catch(Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
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

}
