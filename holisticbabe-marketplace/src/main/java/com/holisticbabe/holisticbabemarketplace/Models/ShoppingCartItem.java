package com.holisticbabe.holisticbabemarketplace.Models;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "purchasable_type", discriminatorType = DiscriminatorType.STRING)
public class ShoppingCartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_shopping_cart_item;
    private String quantity;

    @Column(name = "purchasable_type", insertable = false, updatable = false)
    private String purchasableType;

    @ManyToOne
    @JoinColumn(name = "bootcamp_id")
    private Bootcamp bootcamp;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "id_shopping_cart")
    private ShoppingCart shoppingCart;
}
