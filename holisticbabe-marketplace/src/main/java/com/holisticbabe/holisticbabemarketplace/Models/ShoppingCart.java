package com.holisticbabe.holisticbabemarketplace.Models;


import com.holisticbabe.holisticbabemarketplace.Models.User._User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_shopping_cart;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User user;

}
