package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.React;
import com.holisticbabe.holisticbabemarketplace.Models.Review;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Services.ReactService;
import com.holisticbabe.holisticbabemarketplace.Services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/reacts")
public class ReactController {
    @Autowired
    private ReactService reactService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/like")
    public ResponseEntity<React> addLikeToReview(
            @RequestBody _User user,
            @RequestBody Review review) {
        React react = reactService.addLikeToReview(user, review);
        return ResponseEntity.ok(react);
    }

    @PostMapping("/dislike")
    public ResponseEntity<React> addDislikeToReview(
            @RequestBody _User user,
            @RequestBody Review review) {
        React react = reactService.addDislikeToReview(user, review);
        return ResponseEntity.ok(react);
    }

    @GetMapping("/likes/{reviewId}")
    public ResponseEntity<Long> countLikesForReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        long count = reactService.countLikesForReview(review);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/dislikes/{reviewId}")
    public ResponseEntity<Long> countDislikesForReview(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        long count = reactService.countDislikesForReview(review);
        return ResponseEntity.ok(count);
    }
}


