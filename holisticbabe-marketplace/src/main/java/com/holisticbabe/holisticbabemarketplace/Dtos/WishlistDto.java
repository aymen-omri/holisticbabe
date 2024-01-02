package com.holisticbabe.holisticbabemarketplace.Dtos;

import com.holisticbabe.holisticbabemarketplace.Models.Category;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistDto {
    private Long idUser;
    private Long idProduct;
    private  Long id;
    private String productName;
    private BigDecimal price;
    private String image;
    private String categoryName;
    private  int quantityInStock;
    private LocalDate dateCreated;
}
