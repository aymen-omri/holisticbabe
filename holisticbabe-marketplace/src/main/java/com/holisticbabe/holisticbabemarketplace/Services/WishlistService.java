package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Models.Wishlist;

import java.util.List;

public interface WishlistService {
    List<Wishlist> getAll() ;
     Wishlist addProductToWishlist(Long wishlistId, Long productId) ;
     void removeProductFromWishlist(Long wishlistId, Long productId);
     Wishlist addWishlist(Wishlist wishlist);


        Long countProductsInWishlist(Long wishlistId);

    }
