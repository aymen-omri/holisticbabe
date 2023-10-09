package com.holisticbabe.holisticbabemarketplace.Models.Course;


import java.util.ArrayList;
import java.util.List;

import com.holisticbabe.holisticbabemarketplace.Models.Shared.File;
import com.holisticbabe.holisticbabemarketplace.Models.Shared.Video;

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
    private List<Video> videos = new ArrayList<Video>();
    @OneToMany
    private List<File> files = new ArrayList<File>();
}