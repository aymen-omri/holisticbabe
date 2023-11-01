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
            return ResponseEntity.ok(reviewService.createReview(review));
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

    @PostMapping("/{reviewId}/like")
    public ResponseEntity<Review> addLike(@PathVariable Long reviewId) {
        Review review=reviewService.likeReview(reviewId);
        return ResponseEntity.ok(review);
    }
    @PostMapping("/{reviewId}/dislike")
    public ResponseEntity<Review> addDislike(@PathVariable Long reviewId) {
        Review review=reviewService.dislikeReview(reviewId);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/{ReviewId}/countLike")
    public long countLike(@PathVariable long reviewId){
         return reviewService.countLikeForReview(reviewId);
    }
    @GetMapping("/{ReviewId}/countDisLike")
    public long countDislike(@PathVariable long reviewId){
        return reviewService.countDislikesForReview(reviewId);
    }

}
