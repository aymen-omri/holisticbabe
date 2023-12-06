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
public class _Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_subject;
    @Column(name = "lesson_name")
    private String name;
    private String description;
    @Column(name = "subject_status")
    private int status;
    private LocalDate createdAt;
    @Column(name = "subject_order")
    private int order;
    @ManyToOne
    @JoinColumn(name = "id_course")
    private Course course;
}
