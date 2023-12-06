package com.holisticbabe.holisticbabemarketplace.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Multimedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_multi;
    private String url;
    private String type;
    private String name;
    private String duration;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @ManyToOne
    private Consultant consultant;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private Course course;

    @ManyToOne
    private Lesson lesson;
    @JsonIgnore
    
    @OneToOne(mappedBy = "image")
    private  Category category;
}
