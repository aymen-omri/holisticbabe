package com.holisticbabe.holisticbabemarketplace.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_course;
    private String title;
    private String subtitle;
    private String requirements;
    private String description;
    private int approved;

    @OneToOne
    @JoinColumn(name = "id_multi")
    private Multimedia introImage;

    @ManyToOne
    @JoinColumn(name = "id_language")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User instructor;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;
}
