package com.holisticbabe.holisticbabemarketplace.Services;

import com.holisticbabe.holisticbabemarketplace.Dtos.WishlistDto;
import com.holisticbabe.holisticbabemarketplace.Models.Wishlist;

import java.util.List;

public interface WishlistService {
    List<Wishlist> getAll();

    WishlistDto addProductToWishlist(WishlistDto wishlistDto) ;

    Wishlist addWishlist(Wishlist wishlist);

    List<WishlistDto> getWishlistUserId(Long userId);
     void deleteWishlistById(Long wishlistId);
    int countWishlistItems(Long userId);

}
