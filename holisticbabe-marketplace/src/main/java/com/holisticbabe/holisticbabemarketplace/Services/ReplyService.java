package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Dtos.ReplyDto;

import java.util.List;

public interface ReplyService {
    ReplyDto addReplyToReview(Long reviewId, ReplyDto replyDto);
    List<ReplyDto> getAllRepliesByReviewId(Long reviewId);

}
