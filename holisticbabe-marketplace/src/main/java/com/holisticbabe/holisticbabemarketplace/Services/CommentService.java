package com.holisticbabe.holisticbabemarketplace.Services;

import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Dtos.CommentDto;
import com.holisticbabe.holisticbabemarketplace.Models.Comment;

public interface CommentService {
    List<CommentDto> getCommentsByCourseId(long id);

    CommentDto getCommentById(long id);

    List<CommentDto> getCommentReplies(long id);

    void addComment(Comment comment);

    void addReply(long id, Comment comment);

    void updateComment(long id, Comment comment);

    void deleteComment(long id);
}
