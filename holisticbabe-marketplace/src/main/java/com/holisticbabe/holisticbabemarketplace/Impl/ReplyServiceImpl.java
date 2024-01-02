package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Dtos.ReplyDto;
import com.holisticbabe.holisticbabemarketplace.Dtos.ReviewDto;
import com.holisticbabe.holisticbabemarketplace.Models.Reply;
import com.holisticbabe.holisticbabemarketplace.Models.Review;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.ReplyRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.ReviewRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Services.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    public ReplyDto addReplyToReview(Long reviewId, ReplyDto replyDto) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Optional<_User> optionalUser = userRepository.findById(replyDto.getUserId());

        if (optionalReview.isPresent() && optionalUser.isPresent()) {
            Review review = optionalReview.get();
            _User user = optionalUser.get();

            Reply reply = new Reply();
            reply.setContent(replyDto.getContent());
            review.setDateCreated(replyDto.getDateCreated());
            reply.setReview(review);
            reply.setUser(user);

            replyRepository.save(reply);
            return reply.getDto();
        }

        return null;
    }

    public List<ReplyDto> getAllRepliesByReviewId(Long reviewId) {
        List<Reply> replies = replyRepository.findRepliesByReviewId(reviewId);
        return replies.stream()
                .map(Reply::getDto)
                .collect(Collectors.toList());
    }
}