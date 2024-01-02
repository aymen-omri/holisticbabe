package com.holisticbabe.holisticbabemarketplace.Models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_product;
    @NotBlank(message = "Le nom du produit est requis")
    @Size(min = 3, max = 100)
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
    private String name;
    @NotBlank
    @Size(min = 10)
    private String description;
    private String ShortDescription;
    private int quantityInStock;
    private String sku;
    @NotNull
    private BigDecimal price;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateProduction;
    private int status;
    private int is_approved;


    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotions;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Multimedia> images;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User owner;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductItem> productItems;

    @ManyToOne
    private Promotion promotion;


}
