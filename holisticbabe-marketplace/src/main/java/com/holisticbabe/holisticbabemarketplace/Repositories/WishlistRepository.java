package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query("SELECT COUNT(p) FROM Wishlist w JOIN w.products p WHERE w.id = :wishlistId")
    Long countProductsInWishlist(@Param("wishlistId") Long wishlistId);


}
