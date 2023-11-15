package com.holisticbabe.holisticbabemarketplace.Requests;

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
    private String categoryName;
    private double ratingValue;
    private BigDecimal discountPrice;
    private String firstImage;

}
