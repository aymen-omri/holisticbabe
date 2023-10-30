package com.holisticbabe.holisticbabemarketplace.Services.WishlistService;

import com.holisticbabe.holisticbabemarketplace.Dtos.ProductRepository;
import com.holisticbabe.holisticbabemarketplace.Dtos.UserRepository;
import com.holisticbabe.holisticbabemarketplace.Dtos.WishlistRepository;
import com.holisticbabe.holisticbabemarketplace.Models.Product;
import com.holisticbabe.holisticbabemarketplace.Models.Wishlist;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        try {
            return wishlistRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching all wishlists.");
        }
    }

    public Wishlist addWishlist(Wishlist wishlist) {
        try {
            return wishlistRepository.save(wishlist);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while creating the wishlist.");
        }
    }

    @Override
    public Wishlist addProductToWishlist(Long wishlistId, Long productId) {
        try {
            Wishlist wishlist = wishlistRepository.findById(wishlistId)
                    .orElseThrow(() -> new EntityNotFoundException("Wishlist not found"));

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));

            // Add the product to the wishlist
            wishlist.getProducts().add(product);
            wishlistRepository.save(wishlist);

            return wishlist;
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while adding a product to the wishlist.");
        }
    }

    public Wishlist getWishlistById(Long wishlistId) {
        try {
            return wishlistRepository.findById(wishlistId)
                    .orElseThrow(() -> new EntityNotFoundException("Wishlist not found with ID: " + wishlistId));
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while fetching the wishlist by ID.");
        }
    }

    public void removeProductFromWishlist(Long wishlistId, Long productId) {
        try {
            Wishlist wishlist = wishlistRepository.findById(wishlistId)
                    .orElseThrow(() -> new EntityNotFoundException("Wishlist not found"));

            Product productToRemove = wishlist.getProducts().stream()
                    .filter(product -> Objects.equals(product.getId_product(), productId))
                    .findFirst()
                    .orElseThrow(() -> new EntityNotFoundException("Product not found in wishlist"));

            wishlist.getProducts().remove(productToRemove);
            wishlistRepository.save(wishlist);
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while removing a product from the wishlist.");
        }
    }

    public Long countProductsInWishlist(Long wishlistId) {
        try {
            return wishlistRepository.countProductsInWishlist(wishlistId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("An error occurred while counting the products in the wishlist.");
        }
    }
}
