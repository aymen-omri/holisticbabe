package com.holisticbabe.holisticbabemarketplace.Models.Product;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Models.Shared.Category;
import com.holisticbabe.holisticbabemarketplace.Models.Shared.Multimedia;
import com.holisticbabe.holisticbabemarketplace.Models.User._User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_product;
    private String name;
    private String description;
    private BigDecimal price;
    private String SKU;
    private int quantityInStock;
    private String color;
    private String size;
    private String material;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Multimedia> images = new ArrayList<Multimedia>();

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User owner;

}
