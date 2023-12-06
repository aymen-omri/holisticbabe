package com.holisticbabe.holisticbabemarketplace.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_question;
    private String questionText;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_quiz")
    private Quiz quiz;

    //@JsonIgnore
    @OneToMany(mappedBy = "question")
    private List<_Option> options = new ArrayList<>();

}
