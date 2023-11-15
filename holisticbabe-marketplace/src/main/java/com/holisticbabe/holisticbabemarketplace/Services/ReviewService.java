package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviewsByProductId(Long productId);

    void deleteReview(Long reviewId);

    Review updateReview(Long reviewId, Review updatedReview);

    void createReview(Review review);

    Review getReviewById(Long reviewId);

    List<Review> getAllReviews();

    double calculateProductRating(Long productId);

    int countReviews(Long productId);


}