package com.holisticbabe.holisticbabemarketplace.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.holisticbabe.holisticbabemarketplace.Dtos.WishlistDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private _User user;

 public WishlistDto getWishlistDto(){
     WishlistDto wishlistDto=new WishlistDto();

     wishlistDto.setId(id);
     wishlistDto.setIdUser(user.getId_user());
     wishlistDto.setIdProduct(product.getId_product());
     wishlistDto.setCategoryName(product.getCategory().getName());
     List<Multimedia> images = product.getImages();
     if (!images.isEmpty()) {
         wishlistDto.setImage(images.get(0).getUrl());
     }
     wishlistDto.setPrice(product.getPrice());
     wishlistDto.setProductName(product.getName());
     wishlistDto.setQuantityInStock(product.getQuantityInStock());
     List<ProductItem> productItems = product.getProductItems();
     if (!productItems.isEmpty()) {
         ProductItem productItem = productItems.get(0);
         wishlistDto.setQuantityInStock(productItem.getQuantityInStock());
     }

     return  wishlistDto;
 }
}
