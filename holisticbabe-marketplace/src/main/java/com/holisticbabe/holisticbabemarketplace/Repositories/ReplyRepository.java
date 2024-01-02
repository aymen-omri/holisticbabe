package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
    @Query("SELECT r FROM Reply r WHERE r.review.id_review = :reviewId")
    List<Reply> findRepliesByReviewId(@Param("reviewId") Long reviewId);
}
