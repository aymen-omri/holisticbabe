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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class _Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_option;
    private String optionText;
    private int isCorrect; //0 not correct 1 correct

    @ManyToOne
    @JoinColumn(name = "id_question")
    private Question question;
}
