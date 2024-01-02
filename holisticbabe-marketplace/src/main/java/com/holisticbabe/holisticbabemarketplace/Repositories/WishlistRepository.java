package com.holisticbabe.holisticbabemarketplace.Repositories;

import com.holisticbabe.holisticbabemarketplace.Models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query("SELECT w FROM Wishlist w WHERE w.user.id_user = :userId")
    List<Wishlist> findAllByUserId(@Param("userId") Long userId);
    @Query("SELECT COUNT(w) FROM Wishlist w WHERE w.user.id_user = :userId")
    int countWishlistItems(@Param("userId") Long userId);



}
