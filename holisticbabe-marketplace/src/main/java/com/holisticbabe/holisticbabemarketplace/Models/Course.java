package com.holisticbabe.holisticbabemarketplace.Models;

import java.time.LocalDate;

import jakarta.persistence.Column;
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
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_course;
    private String title;
    private String subtitle;
    private String requirements;
    @Column(length = 10000)
    private String description;
    private String target;
    private LocalDate createdAt;
    private LocalDate deletedAt;
    private LocalDate updatedAt;
    private int status;
    private double price;
    // 0 : not complete //1 : under review //2 : active // 3: rejected // 4:deleted

    @ManyToOne
    @JoinColumn(name = "id_language")
    private Language language;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User instructor;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "id_course_level")
    private CourseLevel level;
}
