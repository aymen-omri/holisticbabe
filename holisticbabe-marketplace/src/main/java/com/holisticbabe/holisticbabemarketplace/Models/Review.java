package com.holisticbabe.holisticbabemarketplace.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_review;
    private String comment;
    private Integer value;
    private String pros;
    private String cons;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User user;

    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

}
