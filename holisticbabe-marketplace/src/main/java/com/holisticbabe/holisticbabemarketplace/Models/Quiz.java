package com.holisticbabe.holisticbabemarketplace.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
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
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_quiz;
    @Column(name = "quiz_name")
    private String name;
    private String description;
    private double scoreToPass;
    @Column(name = "quiz_status")
    private int status;
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "id_subject")
    private _Subject subject;

    // @JsonIgnore
    @OneToMany(mappedBy = "quiz")
    private List<Question> questions = new ArrayList<>();

}
