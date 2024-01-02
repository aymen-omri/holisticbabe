package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Dtos.ReviewDto;
import com.holisticbabe.holisticbabemarketplace.Models.Review;

import java.io.IOException;
import java.util.List;

public interface ReviewService {

    List<Review> getReviewsByProductId(Long productId);

    void deleteReview(Long reviewId);

    Review updateReview(Long reviewId, Review updatedReview);

    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;
    Review getReviewById(Long reviewId);

    List<Review> getAllReviews();

    double calculateProductRating(Long productId);

    int countReviews(Long productId);
    List<ReviewDto> getAllReviewsByProductId(Long productId);

    Double averageRatingByProduct(Long productId);
    Double sumRatingValues();


}