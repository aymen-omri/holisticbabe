package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.ReplyDto;
import com.holisticbabe.holisticbabemarketplace.Services.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Reply")
public class ReplyController {
   @Autowired
   private ReplyService replyService;

    @PostMapping("/{reviewId}/replies")
    public ResponseEntity<ReplyDto> addReplyToReview(@PathVariable Long reviewId, @RequestBody ReplyDto replyDto) {
        ReplyDto addedReply = replyService.addReplyToReview(reviewId, replyDto);

        if (addedReply != null) {
            return new ResponseEntity<>(addedReply, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{reviewId}/replies")
    public ResponseEntity<List<ReplyDto>> getAllRepliesByReviewId(@PathVariable Long reviewId) {
        List<ReplyDto> replies = replyService.getAllRepliesByReviewId(reviewId);

        if (!replies.isEmpty()) {
            return new ResponseEntity<>(replies, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
