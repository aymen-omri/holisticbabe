package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Promotion;
import com.holisticbabe.holisticbabemarketplace.Repositories.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Services.PromotionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;
    @Autowired
    private ProductRepository productRepository;
    @GetMapping
    public ResponseEntity<List<Promotion>> getAllPromotions() {
        List<Promotion> promotions = promotionService.getAllPromotions();
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/{promotionId}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable Long promotionId) {
        Promotion promotion = promotionService.getPromotionById(promotionId);
        return ResponseEntity.ok(promotion);
    }

    @PostMapping
    public ResponseEntity<Promotion> createPromotion(@RequestBody Promotion promotion) {
        Promotion createdPromotion = promotionService.createPromotion(promotion);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPromotion);
    }

    @PutMapping("/{promotionId}")
    public ResponseEntity<Promotion> updatePromotion(
            @PathVariable Long promotionId,
            @RequestBody Promotion promotion) {
        Promotion updatedPromotion = promotionService.updatePromotion(promotionId, promotion);
        return ResponseEntity.ok(updatedPromotion);
    }

    @DeleteMapping("/{promotionId}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long promotionId) {
        promotionService.deletePromotion(promotionId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("promotion/{promotionId}/products/{productId}")
    public ResponseEntity<Promotion> addProductToPromotion(
            @PathVariable Long promotionId,
            @PathVariable Long productId) {
        Promotion promotion = promotionService.addProductToPromotion(promotionId, productId);
        return ResponseEntity.ok(promotion);
    }

    @DeleteMapping("/{promotionId}/products/{productId}")
    public ResponseEntity<String> removeProductFromPromotion(
            @PathVariable Long promotionId,
            @PathVariable Long productId
    ) {
        try {
            promotionService.removeProductFromPromotion(promotionId, productId);
            return ResponseEntity.ok("Product removed from promotion successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/addPromotionByProduct/{productId}")
    public ResponseEntity<Promotion> addPromotionToProduct(@PathVariable Long productId,@RequestBody Promotion promotion) {
        Promotion addedPromotion = promotionService.addPromotionToProduct(productId,promotion);
        return new ResponseEntity<>(addedPromotion, HttpStatus.CREATED);
    }


}