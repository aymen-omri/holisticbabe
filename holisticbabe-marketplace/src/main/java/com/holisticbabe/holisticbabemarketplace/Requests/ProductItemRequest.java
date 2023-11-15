package com.holisticbabe.holisticbabemarketplace.Requests;

import com.holisticbabe.holisticbabemarketplace.Models.VariationOption;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemRequest {
    //private List<String>  variationOptions;
    private List<VariationOption> variationOptions;
    private String SKU;
    private int quantityInStock;
    private BigDecimal price;



}
