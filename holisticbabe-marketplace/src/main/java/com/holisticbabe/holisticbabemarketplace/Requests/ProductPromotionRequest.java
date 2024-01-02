package com.holisticbabe.holisticbabemarketplace.Requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Models.Promotion;
import com.holisticbabe.holisticbabemarketplace.Models._User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPromotionRequest {
    private long id;
    private long id_promotion;
    private String name;
    private String promotionName;
    private String imageUrl;
    private LocalDate dateCreated;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal discount;
    private String status;

}
