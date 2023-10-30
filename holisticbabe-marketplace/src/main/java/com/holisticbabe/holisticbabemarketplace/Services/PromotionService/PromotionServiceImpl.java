package com.holisticbabe.holisticbabemarketplace.Services.PromotionService;

import com.holisticbabe.holisticbabemarketplace.Dtos.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Dtos.PromotionRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Promotion;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Promotion> getAllPromotions() {
        try {
            return promotionRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching all promotions.");
        }
    }

    @Override
    public Promotion getPromotionById(Long promotionId) {
        try {
            return promotionRepository.findById(promotionId)
                    .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching the promotion by ID.");
        }
    }

    @Override
    public Promotion createPromotion(Promotion promotion) {
        try {
            return promotionRepository.save(promotion);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while creating the promotion.");
        }
    }

    @Override
    public Promotion updatePromotion(Long promotionId, Promotion newPromotion) {
        try {
            Promotion existingPromotion = getPromotionById(promotionId);

            existingPromotion.setName(newPromotion.getName());
            existingPromotion.setDescription(newPromotion.getDescription());

            return promotionRepository.save(existingPromotion);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while updating the promotion.");
        }
    }

    @Override
    public void deletePromotion(Long promotionId) {
        try {
            promotionRepository.deleteById(promotionId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while deleting the promotion.");
        }
    }

    @Override
    public Promotion addProductToPromotion(Long promotionId, Long productId) {
        try {
            Promotion promotion = promotionRepository.findById(promotionId)
                    .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            promotion.getProducts().add(product);
            promotion.setActive(true);

            return promotionRepository.save(promotion);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while adding a product to the promotion.");
        }
    }

    @Override
    public void removeProductFromPromotion(Long promotionId, Long productId) {
        try {
            Promotion promotion = promotionRepository.findById(promotionId)
                    .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            promotion.getProducts().remove(product);

            if (promotion.getProducts().isEmpty()) {
                promotion.setActive(false);
            }

            promotionRepository.save(promotion);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while removing a product from the promotion.");
        }
    }
}
