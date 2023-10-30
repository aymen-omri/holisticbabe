package com.holisticbabe.holisticbabemarketplace.Models;


import com.holisticbabe.holisticbabemarketplace.Models.Category;
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

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_course;
    private String title;
    private String subtitle;
    private String requirements;
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User instructor ; 

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
}
