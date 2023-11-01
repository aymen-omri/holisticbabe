package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.product.id_product = ?1")
    List<Review> findReviewsByProductId(Long productId);

    @Query("SELECT count(r) FROM Review r WHERE r.product.id_product = ?1")
    int countReviews(Long productId);

    @Query("SELECT count(r) FROM Review r WHERE r.id_review = :reviewId And r.likeReview = true")
    long countByReviewIdAndLikeIsTrue(long reviewId);

    @Query("SELECT count(r) FROM Review r WHERE r.id_review= :reviewId AND r.dislikeReview=false ")
    long countByReviewIdAndDislikeIsTrue(long reviewId);

}
