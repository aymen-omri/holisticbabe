package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Review;

import java.util.List;

public interface ReviewService {
    Review createReviewInProduct(Long productId, Review review);

    List<Review> getReviewsByProductId(Long productId);

    void deleteReviewInProduct(Long productId, Long reviewId);

    Review updateReviewInProduct(Long productId, Long reviewId, Review updatedReview);

    void deleteReview(Long reviewId);

    Review updateReview(Long reviewId, Review updatedReview);

    Review createReview(Review review);

    Review getReviewById(Long reviewId);

    List<Review> getAllReviews();

    double calculateProductRating(Long productId);

    int countReviews(Long productId);

    Review likeReview(Long reviewId);

    Review dislikeReview(Long reviewId);

    long countDislikesForReview(long reviewId);

    long countLikeForReview(long reviewId);

}