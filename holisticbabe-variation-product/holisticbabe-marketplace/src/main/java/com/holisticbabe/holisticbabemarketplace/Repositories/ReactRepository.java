package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.React;
import com.holisticbabe.holisticbabemarketplace.Models.Review;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactRepository extends JpaRepository<React,Long> {
    React findByUserAndReview(_User user, Review review);
    long countByReviewAndLikeIsTrue(Review review);

    long countByReviewAndDislikeIsTrue(Review review);



}
