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

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_lesson;
    private String lessonName;
    private String description;
    @Column(name = "lesson_status")
    private int status;
    @Column(name = "lesson_order")
    private int order;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "id_subject")
    private _Subject subject;

}
