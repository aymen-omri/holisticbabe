package com.holisticbabe.holisticbabemarketplace.Controllers;

import com.holisticbabe.holisticbabemarketplace.Dtos.WishlistDto;
import com.holisticbabe.holisticbabemarketplace.Models.Wishlist;
import com.holisticbabe.holisticbabemarketplace.Services.WishlistService;
import jakarta.persistence.EntityNotFoundException;
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

    @PostMapping("/add")
    public ResponseEntity<?> addProductToWishlist(@RequestBody WishlistDto wishlistDto) {
        try {
            WishlistDto result = wishlistService.addProductToWishlist(wishlistDto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
        }
    }

    @GetMapping("/user/{userId}")
    public List<WishlistDto> getWishlistByUserId(@PathVariable Long userId) {
        return wishlistService.getWishlistUserId(userId);
    }



    @GetMapping("/count/{userId}")
    public ResponseEntity<Integer> countWishlistItems(@PathVariable Long userId) {
        int count = wishlistService.countWishlistItems(userId);
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/{wishlistId}")
    public void deleteWishlist(@PathVariable Long wishlistId) {
        wishlistService.deleteWishlistById(wishlistId);
    }
}