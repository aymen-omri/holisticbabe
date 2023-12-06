package com.holisticbabe.holisticbabemarketplace.Impl;


import com.holisticbabe.holisticbabemarketplace.Models.React;
import com.holisticbabe.holisticbabemarketplace.Models.Review;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.ReactRepository;
import com.holisticbabe.holisticbabemarketplace.Services.ReactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactServiceImpl implements ReactService {

    @Autowired
    private ReactRepository reactRepository;

    @Override
    public React addLikeToReview(_User user, Review review) {
        return addReactToReview(user, review, true, false);
    }

    @Override
    public React addDislikeToReview(_User user, Review review) {
        return addReactToReview(user, review, false, true);
    }

    @Override
    public long countLikesForReview(Review review) {
        return reactRepository.countByReviewAndLikeIsTrue(review);
    }

    @Override
    public long countDislikesForReview(Review review) {
        return reactRepository.countByReviewAndDislikeIsTrue(review);
    }

    private React addReactToReview(_User user, Review review, boolean like, boolean dislike) {
        React existingReact = reactRepository.findByUserAndReview(user, review);
        if (existingReact != null) {
            existingReact.setLike(like);
            existingReact.setDislike(dislike);
            return reactRepository.save(existingReact);
        } else {
            React newReact = new React();
            newReact.setUser(user);
            newReact.setReview(review);
            newReact.setLike(like);
            newReact.setDislike(dislike);
            return reactRepository.save(newReact);
        }
    }


}

