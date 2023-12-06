package com.holisticbabe.holisticbabemarketplace.Impl;

import java.time.LocalDate;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.holisticbabe.holisticbabemarketplace.Dtos.CommentDto;
import com.holisticbabe.holisticbabemarketplace.Models.Comment;
import com.holisticbabe.holisticbabemarketplace.Repositories.CommentRepository;
import com.holisticbabe.holisticbabemarketplace.Services.CommentService;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<CommentDto> getCommentsByCourseId(long id) {
        return commentRepository.findByCourseId(id).stream().map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList();
    }

    @Override
    public CommentDto getCommentById(long id) {
        return modelMapper.map(commentRepository.findById(id).get(), CommentDto.class);
    }

    @Override
    public List<CommentDto> getCommentReplies(long id) {
        return commentRepository.findByParentComment(id).stream()
                .map(comment -> modelMapper.map(comment, CommentDto.class))
                .toList();
    }

    @Override
    public void addComment(Comment comment) {
        comment.setDateOfComment(LocalDate.now());
        commentRepository.save(comment);
    }

    @Override
    public void addReply(long id, Comment comment) {
        Comment parentComment = commentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Comment not found"));
        comment.setParentComment(parentComment);
        comment.setDateOfComment(LocalDate.now());
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void updateComment(long id, Comment comment) {
        Comment commentToUpdate = commentRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Comment not found"));
        commentToUpdate.setComment(comment.getComment());
    }

    @Override
    public void deleteComment(long id) {
        List<Comment> replies = commentRepository.findByParentComment(id);
        commentRepository.deleteAll(replies);
        commentRepository.deleteById(id);
    }

}
