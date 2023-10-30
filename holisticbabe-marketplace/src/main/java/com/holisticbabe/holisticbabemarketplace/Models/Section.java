package com.holisticbabe.holisticbabemarketplace.Models;


import java.util.ArrayList;
import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Models.Course;
import com.holisticbabe.holisticbabemarketplace.Models.Multimedia;

import jakarta.persistence.Entity;
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
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_section;
    private String title;
    private int order;
    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;
    @OneToMany
    private List<Multimedia> multimedias = new ArrayList<Multimedia>();

}
