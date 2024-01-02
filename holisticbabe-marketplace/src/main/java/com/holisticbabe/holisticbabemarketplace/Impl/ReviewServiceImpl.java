package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Dtos.ReviewDto;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Reply;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.ReplyRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.ReviewRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Review;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Services.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReplyRepository replyRepository;
    public List<Review> getAllReviews() {
        try {
            return reviewRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching reviews.");
        }
    }

    public Review getReviewById(Long reviewId) {
        try {
            return reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new EntityNotFoundException("Review not found"));
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching the review by ID.");
        }
    }

    public ReviewDto giveReview(ReviewDto reviewDto) throws IOException {
        Optional<Product>optionalProduct=productRepository.findById(reviewDto.getProductId());
        Optional<_User>optionalUser=userRepository.findById(reviewDto.getUserId());

        if(optionalProduct.isPresent()&&optionalUser.isPresent()){
            Review review=new Review();
            review.setValue(reviewDto.getValue());
            review.setDateCreated(reviewDto.getDateCreated());
            review.setComment(reviewDto.getComment());
            review.setUser(optionalUser.get());
            review.setProduct(optionalProduct.get());


            return reviewRepository.save(review).getDto();
        }
        return  null;
    }

    public Review updateReview(Long reviewId, Review updatedReview) {
        try {
            Review existingReview = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new EntityNotFoundException("Review not found"));

            existingReview.setValue(updatedReview.getValue());
            existingReview.setComment(updatedReview.getComment());

            return reviewRepository.save(existingReview);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while updating the review.");
        }
    }

    public void deleteReview(Long reviewId) {
        try {
            Review existingReview = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new EntityNotFoundException("Review not found"));
            reviewRepository.delete(existingReview);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while deleting the review.");
        }
    }


    public List<Review> getReviewsByProductId(Long productId) {
        try {
            return reviewRepository.findReviewsByProductId(productId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching reviews by product ID.");
        }
    }

    public double calculateProductRating(Long productId) {
        try {
            List<Review> reviews = reviewRepository.findReviewsByProductId(productId);

            if (reviews.isEmpty()) {
                return 0.0;
            }

            double totalRating = 0.0;
            for (Review review : reviews) {
                totalRating += review.getValue();
            }

            return totalRating / reviews.size();
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;

        } catch (Exception e) {
            e.printStackTrace();
            throw new EntityNotFoundException("I don't have a Rating");
        }
    }

    public int countReviews(Long productId) {
        try {
            int reviews = reviewRepository.countReviews(productId);
            return reviews;
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public List<ReviewDto> getAllReviewsByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findReviewsByProductId(productId);
        return reviews.stream()
                .map(Review::getDto)
                .collect(Collectors.toList());
    }




    public Double averageRatingByProduct(Long productId) {
        return reviewRepository.averageRatingByProduct(productId);
    }

    public Double sumRatingValues() {
        return reviewRepository.sumRatingValues();
    }
}
