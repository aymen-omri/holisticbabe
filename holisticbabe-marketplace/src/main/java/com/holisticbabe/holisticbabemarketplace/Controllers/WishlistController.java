package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Models.Wishlist;
import com.holisticbabe.holisticbabemarketplace.Services.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    @Autowired
    private WishlistService wishlistService;

    @GetMapping
    public List<Wishlist> getAll() {
        return wishlistService.getAll();
    }

    @PostMapping
    public ResponseEntity<Wishlist> createWishlist(@RequestBody Wishlist wishlist) {
        Wishlist createdWishlist = wishlistService.addWishlist(wishlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWishlist);
    }

    @PostMapping("/{wishlistId}/{productId}")
    public ResponseEntity<Void> addProductToWishlist(
            @PathVariable Long wishlistId,
            @PathVariable Long productId) {
        wishlistService.addProductToWishlist(wishlistId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{wishlistId}/products/{productId}")
    public ResponseEntity<Void> removeProductFromWishlist(
            @PathVariable Long wishlistId,
            @PathVariable Long productId) {
        wishlistService.removeProductFromWishlist(wishlistId, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{wishlistId}/count")
    public ResponseEntity<Long> countProductsInWishlist(@PathVariable Long wishlistId) {
        Long count = wishlistService.countProductsInWishlist(wishlistId);
        return ResponseEntity.ok(count);
    }
}