package com.holisticbabe.holisticbabemarketplace.Requests;

import com.holisticbabe.holisticbabemarketplace.Dtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductShop {
    private Long id;
    private String name;
    private BigDecimal price;
    private String categoryName;
    private double averageRating;
    private BigDecimal maxDiscount;
    private String imageUrl;
    private int quantityInStock;



}
