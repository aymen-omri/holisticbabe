package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.Review;
import com.holisticbabe.holisticbabemarketplace.Models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.product.id_product = ?1")
    List<Review> findReviewsByProductId(Long productId);

    @Query("SELECT count(r) FROM Review r WHERE r.product.id_product = ?1")
    int countReviews(Long productId);


    @Query("SELECT AVG(r.value) FROM Review r WHERE r.product.id_product = :productId")
    Double averageRatingByProduct(@Param("productId") Long productId);


    @Query("SELECT COALESCE(SUM(r.value), 0) FROM Review r")
    Double sumRatingValues();

}


