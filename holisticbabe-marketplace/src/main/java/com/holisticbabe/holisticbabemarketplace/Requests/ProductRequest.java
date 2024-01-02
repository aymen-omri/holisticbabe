package com.holisticbabe.holisticbabemarketplace.Requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
public class ProductRequest {
    private long id_product;
    private String name;
    private String description;
    private String ShortDescription;
    private int quantityInStock;
    private BigDecimal price;
    private Date dateProduction;
    private String sku;

    // List<ProductItemRequest> productItemRequestList;
    List<ProductItemRequest> productItems;

}
