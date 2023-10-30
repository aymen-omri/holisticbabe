package com.holisticbabe.holisticbabemarketplace.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.holisticbabe.holisticbabemarketplace.Models.OrderLine;
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

import java.time.LocalDate;

@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_review;
    private String comment;
    private int value;
    private  Boolean likeReview;
    private Boolean dislikeReview;
    private String pros;
    private String cons;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

}
