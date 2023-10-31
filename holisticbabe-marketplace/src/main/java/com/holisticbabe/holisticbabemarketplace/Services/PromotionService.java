package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Promotion;

import java.util.List;

public interface PromotionService {
       List<Promotion> getAllPromotions() ;
       Promotion getPromotionById(Long promotionId);
       Promotion createPromotion(Promotion promotion);

       Promotion updatePromotion(Long promotionId, Promotion newPromotion);
       void deletePromotion(Long promotionId);
       Promotion addProductToPromotion(Long promotionId, Long productId);
       void removeProductFromPromotion(Long promotionId, Long productId);


}
