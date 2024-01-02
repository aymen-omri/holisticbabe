package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Dtos.ProductDto;
import com.holisticbabe.holisticbabemarketplace.Repositories.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.PromotionRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Promotion;

import com.holisticbabe.holisticbabemarketplace.Services.PromotionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
            existingPromotion.setDiscount(newPromotion.getDiscount());
            existingPromotion.setStartDate(newPromotion.getStartDate());
            existingPromotion.setEndDate(newPromotion.getEndDate());
            existingPromotion.setStatus(newPromotion.getStatus());

            return promotionRepository.save(existingPromotion);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while updating the promotion.");
        }
    }

    @Transactional
    public void deletePromotion(Long promotionId) {
        productRepository.updatePromotionIdToNull(promotionId);
        promotionRepository.deleteById(promotionId);

    }

    @Override
    public Promotion addProductToPromotion(Long promotionId, Long productId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                    .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

//promotion.getProducts().add(product);

            return promotionRepository.save(promotion);

    }

    @Override
    public void removeProductFromPromotion(Long promotionId, Long productId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                    .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            //promotion.getProducts().remove(product);
            promotionRepository.save(promotion);

    }
    public Promotion addPromotionToProduct(Long productId, Promotion promotion) {
        Promotion savedPromotion = promotionRepository.save(promotion);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        product.setPromotion(savedPromotion);
        productRepository.save(product);
        return savedPromotion;
    }
    public Promotion getPromotionByProductId(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return product.getPromotion();
        }

        return null;
    }

}
