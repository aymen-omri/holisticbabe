package com.holisticbabe.holisticbabemarketplace.Exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long productId) {
        super("Product not found with ID: " + productId);
    }
}