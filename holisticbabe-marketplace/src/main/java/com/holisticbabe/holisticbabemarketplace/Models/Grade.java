package com.holisticbabe.holisticbabemarketplace.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_grade;
    private double grade;
    private LocalDate gradeDate;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private _User user;

    @ManyToOne
    @JoinColumn(name = "id_quiz")
    private Quiz quiz;

}
