package com.holisticbabe.holisticbabemarketplace.Models;

import com.holisticbabe.holisticbabemarketplace.Dtos.ReviewDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_review;
    private String comment;
    private Integer value;
    private LocalDate dateCreated;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="product_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private _User user;

public ReviewDto getDto(){
  ReviewDto reviewDto=new ReviewDto();

  reviewDto.setId(id_review);
  reviewDto.setValue(value);
  reviewDto.setDateCreated(LocalDate.now());
  reviewDto.setComment(comment);
  reviewDto.setProductId(product.getId_product());
  reviewDto.setUserId(user.getId_user());
  reviewDto.setUserName(user.getLastName());
    if (user.getImage() != null) {
        reviewDto.setImage(user.getImage().getUrl());
    }
 return  reviewDto;
}
}
