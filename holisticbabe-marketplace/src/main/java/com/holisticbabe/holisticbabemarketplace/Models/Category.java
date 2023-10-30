package com.holisticbabe.holisticbabemarketplace.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_category;

    private String name;


}
