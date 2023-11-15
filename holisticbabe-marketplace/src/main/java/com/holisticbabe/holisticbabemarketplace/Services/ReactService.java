package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.React;
import com.holisticbabe.holisticbabemarketplace.Models.Review;
import com.holisticbabe.holisticbabemarketplace.Models._User;

public interface ReactService {

    React addLikeToReview(_User user, Review review);

    React addDislikeToReview(_User user, Review review);
    long countLikesForReview(Review review);
    long countDislikesForReview(Review review);

    }
