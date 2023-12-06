package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.CommentDto;
import com.holisticbabe.holisticbabemarketplace.Models.Comment;
import com.holisticbabe.holisticbabemarketplace.Services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<List<CommentDto>> getCommentsByCourseId(@PathVariable long id) {
        try {
            List<CommentDto> comments = commentService.getCommentsByCourseId(id);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long id) {
        try {
            CommentDto comment = commentService.getCommentById(id);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<List<CommentDto>> getCommentReplies(@PathVariable long id) {
        try {
            List<CommentDto> replies = commentService.getCommentReplies(id);
            return new ResponseEntity<>(replies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody Comment comment) {
        try {
            commentService.addComment(comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<Void> addReply(@PathVariable long id, @RequestBody Comment comment) {
        try {
            commentService.addReply(id, comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable long id, @RequestBody Comment comment) {
        try {
            commentService.updateComment(id, comment);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
