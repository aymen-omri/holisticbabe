package com.holisticbabe.holisticbabemarketplace.Services.ReviewService;

import com.holisticbabe.holisticbabemarketplace.Dtos.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Dtos.ReviewRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Review;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static java.lang.Math.round;

@Service
@Slf4j
public class ReviewServiceImpl implements  ReviewService{

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;



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

    public Review createReview(Review review) {
        try {
            return reviewRepository.save(review);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while creating the review.");
        }
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
    public Review createReviewInProduct(Long productId, Review review) {
        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
            review.setProduct(product);
            return reviewRepository.save(review);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while creating the review.");
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

    public Review updateReviewInProduct(Long productId, Long reviewId, Review updatedReview) {
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

    public void deleteReviewInProduct(Long productId, Long reviewId) {
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
        }catch(EntityNotFoundException ex){
            ex.printStackTrace();
            throw ex;

        }catch (Exception e){
            e.printStackTrace();
            throw  new EntityNotFoundException("I don't have a Rating");
        }
    }

    public int countReviews(Long productId){
        try{
            int reviews=reviewRepository.countReviews(productId);
            return reviews;
        }catch (EntityNotFoundException ex){
            ex.printStackTrace();
            throw ex;
        }
    }
    public  Review likeReview(Long reviewId){
        try{
             Review review=reviewRepository.findById(reviewId)
                     .orElseThrow(()->new RuntimeException("review not found"));
             review.setLikeReview(true);
             review.setDislikeReview(false);
             return reviewRepository.save(review);
        }catch(EntityNotFoundException ex){
            ex.printStackTrace();
            throw  ex;

        }catch (Exception ex){
            ex.printStackTrace();
            throw  new EntityNotFoundException("I don't have a Like");

        }
    }

    public  Review dislikeReview(Long reviewId){
        try{
           Review review=reviewRepository.findById(reviewId)
                   .orElseThrow(()->new RuntimeException("review not found"));
           review.setDislikeReview(true);
           review.setLikeReview(false);
           return reviewRepository.save(review);
        }catch(EntityNotFoundException ex){
            ex.printStackTrace();
            throw  ex;

        }catch (Exception ex){
            ex.printStackTrace();
            throw  new EntityNotFoundException("I don't have a Like");

        }
    }

    @Override
    public long countDislikesForReview(long reviewId) {
        try {
            return reviewRepository.countByReviewIdAndDislikeIsTrue(reviewId);
        }catch (EntityNotFoundException ex){
            ex.printStackTrace();
            throw ex;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("count 0 ");
        }
    }

    @Override
    public long countLikeForReview(long reviewId) {
        try {
            return reviewRepository.countByReviewIdAndLikeIsTrue(reviewId);
        }catch (EntityNotFoundException ex){
            ex.printStackTrace();
            throw ex;
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("count 0 ");
        }
    }



}
