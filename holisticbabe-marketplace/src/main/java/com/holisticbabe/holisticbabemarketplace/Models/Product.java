package com.holisticbabe.holisticbabemarketplace.Models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private String ShortDescription;
    @NotBlank
    @Size(min = 10)
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dateCreated;
    private int isApproved;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Review> reviews;

    @ManyToMany
    private List<Promotion> promotions;
    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Multimedia> images;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User owner;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductItem> productItems;
}
