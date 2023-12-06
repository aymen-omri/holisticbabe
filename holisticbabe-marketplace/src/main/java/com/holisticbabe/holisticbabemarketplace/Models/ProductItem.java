package com.holisticbabe.holisticbabemarketplace.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.util.List;

@Entity
@DynamicUpdate
@DynamicInsert
@Data
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String SKU;
    private int quantityInStock;
    @NotNull
    private BigDecimal price;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany
    private List<VariationOption> variationOptions;

    // Getters and setters
}
