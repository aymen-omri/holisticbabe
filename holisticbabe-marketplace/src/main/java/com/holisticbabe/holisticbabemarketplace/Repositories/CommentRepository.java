package com.holisticbabe.holisticbabemarketplace.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.holisticbabe.holisticbabemarketplace.Models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.parentComment.id_comment =?1")
    List<Comment> findByParentComment(long id);

    @Query("select c from Comment c where c.course.id_course = ?1")
    List<Comment> findByCourseId(long id);
}
