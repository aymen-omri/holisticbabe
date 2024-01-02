package com.holisticbabe.holisticbabemarketplace.Impl;

import com.holisticbabe.holisticbabemarketplace.Dtos.WishlistDto;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import com.holisticbabe.holisticbabemarketplace.Repositories.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Repositories.WishlistRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Wishlist;
import com.holisticbabe.holisticbabemarketplace.Services.WishlistService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Wishlist> getAll() {
            return wishlistRepository.findAll();

    }

    public Wishlist addWishlist(Wishlist wishlist) {
            return wishlistRepository.save(wishlist);
    }


    @Override
    public int countWishlistItems(Long userId) {
        return wishlistRepository.countWishlistItems(userId);
    }

    @Override
    public WishlistDto addProductToWishlist(WishlistDto wishlistDto) {
        Long productId = wishlistDto.getIdProduct();
        Long userId = wishlistDto.getIdUser();
        System.out.println("productId: " + productId);
        System.out.println("userId: " + userId);
        if (productId == null || userId == null) {
            throw new IllegalArgumentException("Product ID and User ID must not be null");
        }
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<_User> optionalUser = userRepository.findById(userId);

        if (optionalProduct.isPresent() && optionalUser.isPresent()) {
            Wishlist wishlist = new Wishlist();
            wishlist.setProduct(optionalProduct.get());
            wishlist.setUser(optionalUser.get());
            Wishlist savedWishlist = wishlistRepository.save(wishlist);
            return savedWishlist.getWishlistDto();
        } else {
            throw new IllegalArgumentException("Product or User not found with the provided IDs");
        }
    }

    public Wishlist getWishlistById(Long wishlistId) {
            return wishlistRepository.findById(wishlistId)
                    .orElseThrow(() -> new EntityNotFoundException("Wishlist not found with ID: " + wishlistId));
    }





    public List<WishlistDto> getWishlistUserId(Long userId) {
        return  wishlistRepository.findAllByUserId(userId).stream().map(Wishlist::getWishlistDto).collect(Collectors.toList());


    }

    public void deleteWishlistById(Long wishlistId) {
        wishlistRepository.deleteById(wishlistId);
    }
}
